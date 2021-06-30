package com.itts.paymentservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itts.paymentservice.constant.PaymentConstant;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.service.WxPatmentService;
import com.itts.paymentservice.utils.QrCodeUtils;
import com.itts.paymentservice.utils.TimeUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

/*
 *@Auther: yukai
 *@Date: 2021/06/30/9:13
 *@Desription:
 */
public class WxPaymentServiceImpl implements WxPatmentService {
    private CloseableHttpClient httpClient;
    @Override
    public Ddxfjl orderInteface(Ddxfjl ddxfjl) throws Exception {
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
                .put("total", ddxfjl.getWxje())
                .put("currency", "CNY");
        //rootNode.putObject("payer").put("openid", "oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");
        objectMapper.writeValue(bos, rootNode);
        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
        String code_url=jsonObject.getString("code_url");
        System.out.println(code_url);
        ImageIO.read(QrCodeUtils.generatorQrCode(code_url));
        return ddxfjl;
    }
}
