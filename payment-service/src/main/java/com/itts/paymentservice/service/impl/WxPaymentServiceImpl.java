package com.itts.paymentservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itts.common.exception.ServiceException;
import com.itts.paymentservice.constant.PaymentConstant;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.service.WxPatmentService;
import com.itts.paymentservice.utils.TimeUtils;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.itts.common.enums.ErrorCodeEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
 *@Auther: yukai
 *@Date: 2021/06/30/9:13
 *@Desription:
 */
@Service
public class WxPaymentServiceImpl implements WxPatmentService {

    private CloseableHttpClient httpClient;
    private AutoUpdateCertificatesVerifier verifier;
    private static String serialNumber = "";
    private static String message = "";
    private static String signature = "";
    public void init(){
        try {
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                    new ByteArrayInputStream(PaymentConstant.wx_privateKey.getBytes("utf-8")));

            //使用自动更新的签名验证器，不需要传入证书
            verifier = new AutoUpdateCertificatesVerifier(
                    new WechatPay2Credentials(PaymentConstant.wx_mchId, new PrivateKeySigner(PaymentConstant.wx_mchSerialNo, merchantPrivateKey)),
                    PaymentConstant.wx_apiV3Key.getBytes("utf-8"));

            httpClient = WechatPayHttpClientBuilder.create()
                    .withMerchant(PaymentConstant.wx_mchId, PaymentConstant.wx_mchSerialNo, merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier))
                    .build();
        } catch (Exception e) {
            throw new ServiceException(PAY_INITORDER_ERROR);
        }
    }

/*    @Before
    public void setup() throws IOException {
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                new ByteArrayInputStream(PaymentConstant.wx_privateKey.getBytes("utf-8")));

        //使用自动更新的签名验证器，不需要传入证书
        verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(PaymentConstant.wx_mchId, new PrivateKeySigner(PaymentConstant.wx_mchSerialNo, merchantPrivateKey)),
                PaymentConstant.wx_apiV3Key.getBytes("utf-8"));

        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(PaymentConstant.wx_mchId, PaymentConstant.wx_mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();
    }*/

/*    @After
    public void after() throws IOException {
        httpClient.close();
    }*/

    public void autoUpdateVerifierTest() throws Exception {
        assertTrue(verifier.verify(serialNumber, message.getBytes("utf-8"), signature));
    }
    /**
    * @Description: 微信预支付接口
    * @Param: [ddxfjl]
    * @return: java.lang.String
    * @Author: yukai
    * @Date: 2021/6/30
    */
    @Override
    public String orderInteface(Ddxfjl ddxfjl) {
        init();
        String bodyAsString =null;
        try {
            URIBuilder uriBuilder = new URIBuilder(PaymentConstant.wx_native_transactions_url);
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type","application/json; charset=utf-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            //获取当前时间两小时后的时间
            String format = TimeUtils.DTF.format(LocalDateTime.now().plusHours(2));
            rootNode.put("mchid",PaymentConstant.wx_mchId)
                    .put("appid",PaymentConstant.wx_appid)
                    .put("description", ddxfjl.getXfsm())
                    //测试暂定固定值，实际开发需接入参数(商户订单号)
                    .put("out_trade_no", ddxfjl.getBh())
                    .put("notify_url", PaymentConstant.wx_notify_url)
                    //交易结束时间(当前时间向后两小时)
                    .put("time_expire",format);
            rootNode.putObject("amount")
                    .put("total", ddxfjl.getZje().multiply(new BigDecimal(100)))
                    .put("currency", "CNY");
            //rootNode.putObject("payer").put("openid", "oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");
            objectMapper.writeValue(bos, rootNode);
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            bodyAsString=EntityUtils.toString(response.getEntity());
            httpClient.close();
        } catch (Exception e) {
            throw new ServiceException(PAY_TRANSACTIONS_ERROR);
        }
        System.out.println(bodyAsString);
        JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
        String code_url=jsonObject.getString("code_url");
        System.out.println(code_url);

        //ImageIO.read(QrCodeUtils.generatorQrCode(code_url));
        return code_url;
    }


    /**
    * @Description: 微信支付查询订单接口
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/6/30
    */
    @Override
    public String selectOrder(Ddxfjl ddxfjl){
        init();
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
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(6000)
                    .setConnectTimeout(6000)
                    .setConnectionRequestTimeout(6000).build();
            httpget.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpget);
            bodyAsString=EntityUtils.toString(response.getEntity());
            System.out.println(bodyAsString);
            httpClient.close();
        } catch (Exception e) {
            throw new ServiceException(PAY_SELECTORDER_ERROR);
        }
        /*
        trade_state
        交易状态，枚举值：
        SUCCESS：支付成功
        REFUND：转入退款
        NOTPAY：未支付
        CLOSED：已关闭
        REVOKED：已撤销（付款码支付）
        USERPAYING：用户支付中（付款码支付）
        PAYERROR：支付失败(其他原因，如银行返回失败)
        ACCEPT：已接收，等待扣款
        示例值：SUCCESS
         */
        JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
        String trade_state=jsonObject.getString("trade_state");
        System.out.println(trade_state);
        return trade_state;
    }
    /**
     * @Description: 微信支付关闭订单接口
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/2
     */
    @Override
    public int closeOrder(Ddxfjl ddxfjl) {
        init();
        int statusCode=500;
        try {
            URIBuilder uriBuilder = new URIBuilder(PaymentConstant.wx_native_selectTrade_url+ddxfjl.getBh()+"/close");
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type","application/json; charset=utf-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("mchid",PaymentConstant.wx_mchId);
            objectMapper.writeValue(bos, rootNode);
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                if (response.getStatusLine() != null) {
                    statusCode = response.getStatusLine().getStatusCode();
                }
            }
            httpClient.close();
        } catch (Exception e) {
            throw new ServiceException(PAY_CLOSEORDER_ERROR);
        }
/*        JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
        String trade_state=jsonObject.getString("trade_state");
        System.out.println(trade_state);*/
        //204为正确码，其他为错误码
        return statusCode;
    }
    //获取平台证书方法
    @Test
    public void getCertificateTest() throws Exception {
        URIBuilder uriBuilder = new URIBuilder("https://api.mch.weixin.qq.com/v3/certificates");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpResponse response1 = httpClient.execute(httpGet);
        assertEquals(200, response1.getStatusLine().getStatusCode());
        try {
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }
    }


}
