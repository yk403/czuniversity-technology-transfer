package com.itts.userservice.controller.sjb;

import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.sjb.Bjg;
import com.itts.userservice.model.sjb.Bzd;
import com.itts.userservice.model.sjb.Sjb;
import com.itts.userservice.service.sjb.SjbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/16
 */
@Api(tags = "数据表管理")
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/sjb")
@RestController
public class SjbController {

    @Autowired
    private SjbService sjbService;

    /**
     * 获取数据表列表
     */
    @ApiOperation(value = "获取数据表列表")
    @GetMapping("/find/tables/")
    public ResponseUtil findTables(@ApiParam("当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @ApiParam("每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @ApiParam("数据表名称") @RequestParam(value = "name", required = false) String name) {

        PageInfo<Sjb> tables = sjbService.findTables(pageNum, pageSize, name);

        return ResponseUtil.success(tables);
    }

    /**
     * 获取数据表结构详情
     */
    @ApiOperation(value = "获取数据表结构详情")
    @GetMapping("/get/table/detail/")
    public ResponseUtil showTables(@ApiParam("数据表名称") @RequestParam(value = "tableName") String tableName) {

        Bjg bjg = sjbService.getTableDetails(tableName);

        return ResponseUtil.success(bjg);
    }
}