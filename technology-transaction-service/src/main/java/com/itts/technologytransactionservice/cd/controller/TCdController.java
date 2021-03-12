package com.itts.technologytransactionservice.cd.controller;


import com.alibaba.fastjson.JSON;
import com.itts.technologytransactionservice.cd.model.TCd;
import com.itts.technologytransactionservice.cd.service.TCdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lym
 * @since 2021-03-12
 */
@Api(tags = "获取tcd")
@RestController
@RequestMapping("/api/tCd")
public class TCdController {

    @Autowired
    private TCdService service;

    @GetMapping("/get/list/")
    @ApiOperation(value = "获取列表")
    public Object getList() {

        List<TCd> list = service.getList();

        return JSON.toJSON(list);
    }

}

