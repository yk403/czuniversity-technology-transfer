package com.itts.personTraining.controller;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.request.yh.RegisterRequest;
import com.itts.personTraining.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping(SystemConstant.BASE_URL)
@RestController
@Api(tags = "注册管理")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    /**
     * 用户注册（门户端）
     *
     * @param
     * @return
     * @author liuyingming
     */
    @PostMapping("/register/")
    @ApiOperation(value = "用户注册")
    public ResponseUtil register(@RequestBody RegisterRequest request) {
        ResponseUtil register = registerService.register(request);
        return register;
    }
}
