package com.itts.paymentservice.feign.userservice;

import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

@FeignClient(value = "user-service")
public interface YhService {

    @GetMapping(ADMIN_BASE_URL + "/v1/yh/getBy/{id}")
    ResponseUtil getById(@PathVariable("id") Long id);
}
