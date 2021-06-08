package com.itts.personTraining.feign.paymentservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/8
 */
@FeignClient("payment-service")
public interface OrderFeignService {

    @GetMapping(SystemConstant.BASE_URL + "/v1/order/rpc/by/user/")
    ResponseUtil getByUserId(@RequestHeader("token") String token);
}
