package com.itts.paymentservice.controller.payment;

import com.itts.common.constant.SystemConstant;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@Auther: yukai
 *@Date: 2021/06/30/8:48
 *@Desription:
 */
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/wxpayment")
@Api(tags = "微信支付接口")
public class WxPaymentController {

}
