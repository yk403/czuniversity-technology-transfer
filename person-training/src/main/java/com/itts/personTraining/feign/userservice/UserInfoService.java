package com.itts.personTraining.feign.userservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "user-service")
public interface UserInfoService {

    /**
     * 获取当前登陆用户信息
     */
    @GetMapping(SystemConstant.BASE_URL + "/v1/yh/get/")
    ResponseUtil get(@RequestHeader(name = "token") String token);
}
