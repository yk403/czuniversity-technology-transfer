package com.itts.personTraining.service;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.request.yh.RegisterRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "user-service")
public interface RegisterService {

    /**
     * 用户注册（门户端）
     *
     * @param
     * @return
     * @author liuyingming
     */
    @PostMapping(SystemConstant.BASE_URL + "/v1/api/register/")
    @ApiOperation(value = "用户注册")
    ResponseUtil register(@RequestBody RegisterRequest request);
}
