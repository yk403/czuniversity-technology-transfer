package com.itts.userservice.controller.js.user;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.service.js.JsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/8
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/js")
public class JsController {

    @Autowired
    private JsService jsService;

    @ApiOperation(value = "通过用户ID获取角色")
    @GetMapping("/get/by/user/")
    public ResponseUtil getByUserId(@RequestParam("userId") Long userId) {

        List<Js> jsList = jsService.findByUserId(userId);

        return ResponseUtil.success(jsList);
    }
}