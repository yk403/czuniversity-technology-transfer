package com.itts.technologytransactionservice.feign.userservice;



import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-service")
public interface UserInfoService {
    /**
     * 获取当前用户
     */
    @GetMapping(SystemConstant.BASE_URL + "/v1/yh/get/")
    ResponseUtil get();
    /**
     * 根据id获取用户
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/yh/get/{id}")
    ResponseUtil getUser(@RequestParam(value = "id") Long id);
    /**
     * 更新用户信息
     */
    /*@PutMapping(SystemConstant.BASE_URL+"/v1/yh/update/")
    ResponseUtil update(@RequestBody UpdateUserRequest updateUserRequest);*/
}
