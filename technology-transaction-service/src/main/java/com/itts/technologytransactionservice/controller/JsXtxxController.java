package com.itts.technologytransactionservice.controller;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.JsXtxx;
import com.itts.technologytransactionservice.service.JsXtxxService;
import com.itts.technologytransactionservice.service.LyBmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/*
 *@Auther: yukai
 *@Date: 2021/07/06/10:00
 *@Desription:
 */
@RequestMapping(BASE_URL + "/v1/JsXtxx")
@Api(value = "JsXtxxController", tags = "系统消息管理")
@RestController
public class JsXtxxController {
    @Autowired
    private JsXtxxService jsXtxxService;
    /**
     * 获取列表
     */
    @ApiOperation(value = "获取列表")
    @PostMapping("/list")
    public ResponseUtil find(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(jsXtxxService.findJsXtxx(params));
    }

}
