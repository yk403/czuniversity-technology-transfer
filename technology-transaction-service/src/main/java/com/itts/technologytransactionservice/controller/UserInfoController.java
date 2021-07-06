package com.itts.technologytransactionservice.controller;

import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.itts.common.constant.SystemConstant.*;

@RequestMapping(BASE_URL+"/v1/userInfo")
@Api(value = "UserInfoController", tags = "技术交易用户服务")
@RestController
public class UserInfoController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;
    /**
     * 获取用户信息
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/get/")
    @ApiOperation(value = "获取用户信息")
    public ResponseUtil get(HttpServletRequest request) {
        return userInfoService.get();
    }
    /**
     * 获取用户信息
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取用户信息")
    public ResponseUtil getUser(@PathVariable("id") Long id) {
        return userInfoService.getUser(id);
    }
}
