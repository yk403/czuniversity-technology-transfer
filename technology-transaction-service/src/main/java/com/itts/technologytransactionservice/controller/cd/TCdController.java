package com.itts.technologytransactionservice.controller.cd;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.feign.payment.PaymentService;
import com.itts.technologytransactionservice.model.cd.TCd;
import com.itts.technologytransactionservice.service.cd.TCdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseUtil getList() {

        List<TCd> list = service.getList();

        return ResponseUtil.success(list);
    }

    @GetMapping("/get/by/page/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil getByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        PageInfo<TCd> page = service.getByPage(pageNum, pageSize);

        return ResponseUtil.success(page);
    }

    @GetMapping("/test/feign/")
    public ResponseUtil testFeign() {

        ResponseUtil test = service.testFeign();

        return test;
    }

}

