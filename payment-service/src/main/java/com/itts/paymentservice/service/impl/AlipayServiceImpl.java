package com.itts.paymentservice.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.itts.paymentservice.constant.PaymentConstant;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.service.AlipayService;
import org.springframework.stereotype.Service;

/*
 *@Auther: yukai
 *@Date: 2021/07/05/10:26
 *@Desription:
 */
@Service
public class AlipayServiceImpl  implements AlipayService {

    @Override
    public String payInteface(Ddxfjl ddxfjl) throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(PaymentConstant.alipay_gatewayUrl, PaymentConstant.alipay_app_id, PaymentConstant.alipay_merchant_private_key, "json", PaymentConstant.alipay_charset, PaymentConstant.alipay_public_key, PaymentConstant.alipay_sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(PaymentConstant.alipay_return_url);
        alipayRequest.setNotifyUrl(PaymentConstant.alipay_notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(ddxfjl.getBh().getBytes("ISO-8859-1"),"UTF-8");
        //String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //付款金额，必填
        String total_amount = new String(ddxfjl.getZje().toString().getBytes("ISO-8859-1"),"UTF-8");
        //String total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
        //订单名称，必填
        String subject = new String(ddxfjl.getDdmc().getBytes("ISO-8859-1"),"UTF-8");
        //String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
        //商品描述，可空
        String body = new String(ddxfjl.getXfsm().getBytes("ISO-8859-1"),"UTF-8");
        //String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //输出
        System.out.println(result);
        return result;
    }
}
