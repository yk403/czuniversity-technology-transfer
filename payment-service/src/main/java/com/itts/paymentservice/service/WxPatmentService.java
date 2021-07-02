package com.itts.paymentservice.service;

import com.itts.paymentservice.model.ddxfjl.Ddxfjl;

/*
 *@Auther: yukai
 *@Date: 2021/06/30/9:03
 *@Desription:
 */
public interface WxPatmentService {
    /**
     * 微信预支付接口
     */
    String orderInteface(Ddxfjl ddxfjl);

    /**
     * 查询订单
     */
    String selectOrder(Ddxfjl ddxfjl);

    /**
     * 关闭订单
     */
    int closeOrder(Ddxfjl ddxfjl);
}
