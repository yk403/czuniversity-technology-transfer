package com.itts.personTraining.feign.userservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.request.feign.UpdateUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service")
public interface UserFeignService {

    /**
     * 获取当前登陆用户信息
     */
    @GetMapping(SystemConstant.BASE_URL + "/v1/yh/get/")
    ResponseUtil get();

    /**
     * 获取用户所属系统
     */
    @GetMapping(SystemConstant.BASE_URL + "/v1/yh/find/systems/")
    ResponseUtil findUserSystems();

    /**
     * 更新用户信息
     */
    @PutMapping(SystemConstant.BASE_URL + "/v1/yh/update/")
    ResponseUtil update(@RequestBody UpdateUserRequest updateUserRequest);


}
