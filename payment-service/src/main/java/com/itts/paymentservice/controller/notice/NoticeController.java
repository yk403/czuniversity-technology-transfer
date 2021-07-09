package com.itts.paymentservice.controller.notice;

import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.paymentservice.constant.PaymentConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author：yukai
 * @Description：支付通知controller
 * @Date: 2021/6/30
 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    /**
    * @Description: 微信通知接口
    * @Param: [request]
    * @return: com.itts.common.utils.common.ResponseUtil
    * @Author: yukai
    * @Date: 2021/7/5
    */
    @PostMapping("/get/")
    public ResponseUtil get(HttpServletRequest request) throws Exception {
        //读取参数
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream( )));
        String line;
        StringBuilder sb = new StringBuilder( );
        while ((line = br.readLine( )) != null) {
            sb.append(line);
        }
        if (StringUtils.isBlank(sb)) {
            return ResponseUtil.success("test");
        }
        //支付结果通知的xml格式数据
        String notifyData = sb.toString( );
/*        MyWxPayConfig myWxPayConfig = new MyWxPayConfig( );
        WXPay wxpay = new WXPay(myWxPayConfig);

        Map< String, String > notifyMap = WXPayUtil.xmlToMap(notifyData);
        System.out.println(notifyMap);

        //验证签名是否正确
        if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
            //支付成功
            if ("SUCCESS".equals(notifyMap.get("result_code"))) {
                //商户订单号
                String out_trade_no = notifyMap.get("out_trade_no");
                //.... = notifyMap.get("out_trade_no");
                //你个人需要写的业务
            } else {
                //log.info("微信支付回调函数：支付失败");
            }
        } else {
            //log.info("微信支付回调函数：微信签名错误");
        }*/
        System.out.println(notifyData);
        return ResponseUtil.success("notice");
    }
    /**
    * @Description: 支付宝通知接口notify_url
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/5
    */
    @PostMapping("/alipaypost/")
    public ResponseUtil alipaypost(HttpServletRequest request, HttpServletResponse out)throws Exception{
//获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter=requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, PaymentConstant.alipay_public_key, PaymentConstant.alipay_charset, PaymentConstant.alipay_sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            System.out.println("success");

        }else {//验证失败
            System.out.println("fail");

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }
        return ResponseUtil.success("test");
    }
    /**
     * @Description: 支付宝通知接口return_url
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/5
     */
    @GetMapping("/alipayget/")
    public ResponseUtil alipayget(HttpServletRequest request, HttpServletResponse response)throws Exception{
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, PaymentConstant.alipay_public_key, PaymentConstant.alipay_charset, PaymentConstant.alipay_sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
        }else {
            System.out.println("验签失败");
        }
        return ResponseUtil.success("test");
    }
}