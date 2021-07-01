package com.itts.paymentservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;

import java.net.URISyntaxException;

/*
 *@Auther: yukai
 *@Date: 2021/06/30/9:03
 *@Desription:
 */
public interface WxPatmentService {
    /**
    * @Description: 微信预支付接口
    * @Param: []
    * @return: com.itts.paymentservice.model.ddxfjl.Ddxfjl
    * @Author: yukai
    * @Date: 2021/6/30
    */
    String orderInteface(Ddxfjl ddxfjl) throws URISyntaxException, Exception;
    Object selectOrder() throws Exception;
}
