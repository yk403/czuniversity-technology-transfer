package com.itts.personTraining.feign.userservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.request.feign.UpdateUserRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

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

    @GetMapping(SystemConstant.BASE_URL + "/v1/yh/get/{id}")
    ResponseUtil getById(@PathVariable("id") Long id);
    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping(ADMIN_BASE_URL + "/v1/yh/delete/{id}")
    ResponseUtil delete(@PathVariable("id") Long id);


}
