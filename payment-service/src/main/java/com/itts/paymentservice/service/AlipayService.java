package com.itts.paymentservice.service;

import com.alipay.api.response.AlipayTradePagePayResponse;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;

/*
 *@Auther: yukai
 *@Date: 2021/07/05/10:26
 *@Desription:
 */
public interface AlipayService {
    /**
     * 支付宝支付接口
     */
    String payInteface(Ddxfjl ddxfjl) throws Exception;
}
