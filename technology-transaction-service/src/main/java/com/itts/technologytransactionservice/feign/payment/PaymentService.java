package com.itts.technologytransactionservice.feign.payment;

import com.itts.common.utils.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author：lym
 * @Description：
 * @Date: 2021/3/12
 */
@FeignClient(value = "payment-service")
public interface PaymentService {

    @GetMapping("/api/test/get/")
    ResponseUtil test();

}
