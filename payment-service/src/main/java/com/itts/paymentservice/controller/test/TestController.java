package com.itts.paymentservice.controller.test;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author：lym
 * @Description：测试controller
 * @Date: 2021/3/12
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

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
        return ResponseUtil.success("test");
    }
}