package com.itts.technologytransactionservice.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/3/27
 * @Version: 1.0.0
 * @Description: 用户管理
 */
@RequestMapping(BASE_URL+"/v1/User")
@Api(value = "UserController", tags = "用户管理")
@RestController
public class UserController {

}
