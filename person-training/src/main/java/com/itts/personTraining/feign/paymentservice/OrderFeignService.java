package com.itts.personTraining.feign.paymentservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.request.ddxfjl.AddDdxfjlRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/8
 */
@FeignClient("payment-service")
public interface OrderFeignService {

    /**
     * 通过用户ID获取用户订单
     */
    @GetMapping(SystemConstant.BASE_URL + "/v1/ddxfjl/rpc/by/user/")
    ResponseUtil getByUserId();

    /**
     * 通过订单编号获取
     */
    @GetMapping(SystemConstant.BASE_URL + "/v1/ddxfjl/get/by/code/")
    ResponseUtil getByCode(@RequestParam("code") String code);

    /**
     * 创建订单
     */
    @PostMapping(SystemConstant.BASE_URL + "/v1/ddxfjl/add/")
    ResponseUtil createOrder(@RequestBody AddDdxfjlRequest addDdxfjlRequest);
}
