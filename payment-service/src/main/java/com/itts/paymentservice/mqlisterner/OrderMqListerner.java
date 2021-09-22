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
 * @Description：
 * @Author：lym
 * @Date: 2021/6/10
 */
@RabbitListener(queues = "${mqconfig.order_release_queue}")
@Component
@Slf4j
public class OrderMqListerner {

    @Autowired
    private DdxfjlService ddxfjlService;

    /**
     * 检查订单是否已支付，如果未支付半小时自动取消订单
     */
    @RabbitHandler
    public void releaseOrderMessage(String orderNo, Message message, Channel channel) throws IOException {

        Ddxfjl ddxfjl = ddxfjlService.getOne(new QueryWrapper<Ddxfjl>().eq("bh", orderNo));

        long msgTag = message.getMessageProperties().getDeliveryTag();

        if (ddxfjl == null) {

            log.info("【订单支付服务：检查订单是否已支付】订单不存在， 订单编号{}", orderNo);
            //消费消息
            channel.basicAck(msgTag, false);
            return;
        }

        if (Objects.equals(OrderStatusEnum.PAID.getKey(), ddxfjl.getZt())) {

            log.info("【订单支付服务：检查订单是否已支付】订单已支付， 订单编号{}", orderNo);
            //消费消息
            channel.basicAck(msgTag, false);
            return;
        }
        //调用第三方支付接口查询订单状态，如果支付成功，更新订单状态， 否则取消订单
        if (Objects.equals(OrderStatusEnum.PENDING.getKey(), ddxfjl.getZt())) {

            log.info("【订单支付服务：检查订单是否已支付】订单未支付， 订单编号{}", orderNo);
            if(Objects.equals(ddxfjl.getZffs(),"aLiPay")){
                try {
                    //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型）
                    AlipayClient alipayClient = new DefaultAlipayClient(PaymentConstant.alipay_gatewayUrl, PaymentConstant.alipay_app_id, PaymentConstant.alipay_merchant_private_key,new AlipayConfig().getFormat() , PaymentConstant.alipay_charset, PaymentConstant.alipay_public_key,PaymentConstant.alipay_sign_type);
                    AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
                    alipayTradeQueryRequest.setBizContent("{" + "\"out_trade_no\":\""+orderNo+"\"" + "}");
                    AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayTradeQueryRequest);
                    if(alipayTradeQueryResponse.isSuccess()){
                        switch (alipayTradeQueryResponse.getTradeStatus()) // 判断交易结果
                        {
                            case "TRADE_FINISHED": // 交易结束并不可退款
                            case "TRADE_SUCCESS": // 交易支付成功
                                ddxfjl.setZt(OrderStatusEnum.COMPLETED.getKey());
                                ddxfjl.setZfsj(new Date());
                                break;
                            case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
                                ddxfjl.setZt(OrderStatusEnum.CANCELLED.getKey());
                                ddxfjl.setQxsj(new Date());
                                break;
                            default:
                                break;
                        }
                         //更新表记录
                        ddxfjlService.updateById(ddxfjl);
                        //消费消息
                        channel.basicAck(msgTag, false);
                        return ;
                    } else {
                        ddxfjl.setZt(OrderStatusEnum.CANCELLED.getKey());
                        ddxfjl.setQxsj(new Date());
                        //更新表记录
                        ddxfjlService.updateById(ddxfjl);
                        //消费消息
                        channel.basicAck(msgTag, false);
                    }
                } catch (AlipayApiException e) {
                    channel.basicNack(msgTag,false,true);
                    e.printStackTrace();
                }
            }else if(Objects.equals(ddxfjl.getZffs(),"wechat")){
                CloseableHttpClient httpClient = null;
                AutoUpdateCertificatesVerifier verifier;
                try {
                    PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(PaymentConstant.wx_privateKey.getBytes("utf-8")));
                    //使用自动更新的签名验证器，不需要传入证书
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
                    //设置超时时间
                    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).setConnectionRequestTimeout(6000).build();
                    httpget.setConfig(requestConfig);
                    CloseableHttpResponse response = httpClient.execute(httpget);
                    bodyAsString=EntityUtils.toString(response.getEntity());
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    channel.basicNack(msgTag,false,true);
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
                //更新表记录
                ddxfjlService.updateById(ddxfjl);
                //消费消息
                channel.basicAck(msgTag, false);
            }
        }
        //消费消息
        channel.basicAck(msgTag, false);
    }
}