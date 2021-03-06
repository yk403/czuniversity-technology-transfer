package com.itts.paymentservice.mqlisterner;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itts.common.exception.ServiceException;
import com.itts.paymentservice.constant.PaymentConstant;
import com.itts.paymentservice.enums.OrderStatusEnum;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.service.DdxfjlService;
import com.itts.paymentservice.utils.TimeUtils;
import com.rabbitmq.client.Channel;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.*;

import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * @Description???
 * @Author???lym
 * @Date: 2021/6/10
 */
@RabbitListener(queues = "${mqconfig.order_release_queue}")
@Component
@Slf4j
public class OrderMqListerner {

    @Autowired
    private DdxfjlService ddxfjlService;

    /**
     * ????????????????????????????????????????????????????????????????????????
     */
    @RabbitHandler
    public void releaseOrderMessage(String orderNo, Message message, Channel channel) throws IOException {

        Ddxfjl ddxfjl = ddxfjlService.getOne(new QueryWrapper<Ddxfjl>().eq("bh", orderNo));

        long msgTag = message.getMessageProperties().getDeliveryTag();

        if (ddxfjl == null) {

            log.info("???????????????????????????????????????????????????????????????????????? ????????????{}", orderNo);
            //????????????
            channel.basicAck(msgTag, false);
            return;
        }

        if (Objects.equals(OrderStatusEnum.PAID.getKey(), ddxfjl.getZt())) {

            log.info("???????????????????????????????????????????????????????????????????????? ????????????{}", orderNo);
            //????????????
            channel.basicAck(msgTag, false);
            return;
        }
        //?????????????????????????????????????????????????????????????????????????????????????????? ??????????????????
        if (Objects.equals(OrderStatusEnum.PENDING.getKey(), ddxfjl.getZt())) {

            log.info("???????????????????????????????????????????????????????????????????????? ????????????{}", orderNo);
            if(Objects.equals(ddxfjl.getZffs(),"aLiPay")){
                try {
                    //???????????????????????????????????????????????????appid?????????????????????????????????????????????????????????????????????
                    AlipayClient alipayClient = new DefaultAlipayClient(PaymentConstant.alipay_gatewayUrl, PaymentConstant.alipay_app_id, PaymentConstant.alipay_merchant_private_key,new AlipayConfig().getFormat() , PaymentConstant.alipay_charset, PaymentConstant.alipay_public_key,PaymentConstant.alipay_sign_type);
                    AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
                    alipayTradeQueryRequest.setBizContent("{" + "\"out_trade_no\":\""+orderNo+"\"" + "}");
                    AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayTradeQueryRequest);
                    if(alipayTradeQueryResponse.isSuccess()){
                        switch (alipayTradeQueryResponse.getTradeStatus()) // ??????????????????
                        {
                            case "TRADE_FINISHED": // ???????????????????????????
                            case "TRADE_SUCCESS": // ??????????????????
                                ddxfjl.setZt(OrderStatusEnum.COMPLETED.getKey());
                                ddxfjl.setZfsj(new Date());
                                break;
                            case "TRADE_CLOSED": // ?????????????????????????????????????????????????????????
                                ddxfjl.setZt(OrderStatusEnum.CANCELLED.getKey());
                                ddxfjl.setQxsj(new Date());
                                break;
                            default:
                                break;
                        }
                         //???????????????
                        ddxfjlService.updateById(ddxfjl);
                        //????????????
                        channel.basicAck(msgTag, false);
                        return ;
                    } else {
                        ddxfjl.setZt(OrderStatusEnum.CANCELLED.getKey());
                        ddxfjl.setQxsj(new Date());
                        //???????????????
                        ddxfjlService.updateById(ddxfjl);
                        //????????????
                        channel.basicAck(msgTag, false);
                    }
                } catch (AlipayApiException e) {
                    channel.basicNack(msgTag,false,false);
                    e.printStackTrace();
                }
            }else if(Objects.equals(ddxfjl.getZffs(),"wechat")){
                CloseableHttpClient httpClient = null;
                AutoUpdateCertificatesVerifier verifier;
                try {
                    PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(PaymentConstant.wx_privateKey.getBytes("utf-8")));
                    //????????????????????????????????????????????????????????????
                    verifier = new AutoUpdateCertificatesVerifier(new WechatPay2Credentials(PaymentConstant.wx_mchId, new PrivateKeySigner(PaymentConstant.wx_mchSerialNo, merchantPrivateKey)), PaymentConstant.wx_apiV3Key.getBytes("utf-8"));
                    httpClient = WechatPayHttpClientBuilder.create().withMerchant(PaymentConstant.wx_mchId, PaymentConstant.wx_mchSerialNo, merchantPrivateKey).withValidator(new WechatPay2Validator(verifier)).build();
                } catch (Exception e) {
                    channel.basicNack(msgTag,false,true);
                    e.printStackTrace();
                }
                String bodyAsString =null;
                try {
                    URIBuilder uriBuilder = new URIBuilder(PaymentConstant.wx_native_selectTrade_url+ddxfjl.getBh());
                    Map<String, String> para=new HashMap<>();
                    para.put("mchid", PaymentConstant.wx_mchId);
                    Set<String> set = para.keySet();
                    for(String key: set){
                        uriBuilder.setParameter(key, para.get(key));
                    }
                    HttpGet httpget = new HttpGet(uriBuilder.build());
                    //??????????????????
                    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).setConnectionRequestTimeout(6000).build();
                    httpget.setConfig(requestConfig);
                    CloseableHttpResponse response = httpClient.execute(httpget);
                    bodyAsString=EntityUtils.toString(response.getEntity());
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    ddxfjl.setZt(OrderStatusEnum.CANCELLED.getKey());
                    ddxfjl.setQxsj(new Date());
                    //???????????????
                    ddxfjlService.updateById(ddxfjl);
                    channel.basicNack(msgTag,false,false);
                }
                JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
                String trade_state=jsonObject.getString("trade_state");
                switch (trade_state){
                    case "SUCCESS":
                        ddxfjl.setZt(OrderStatusEnum.COMPLETED.getKey());
                        ddxfjl.setZfsj(new Date());
                        break;
                    case "NOTPAY":
                    case "CLOSED":
                        ddxfjl.setZt(OrderStatusEnum.CANCELLED.getKey());
                        ddxfjl.setQxsj(new Date());
                        break;
                    default:
                        break;
                }
                //???????????????
                ddxfjlService.updateById(ddxfjl);
                //????????????
                channel.basicAck(msgTag, false);
            }
        }
        //????????????
        channel.basicAck(msgTag, false);
    }
}