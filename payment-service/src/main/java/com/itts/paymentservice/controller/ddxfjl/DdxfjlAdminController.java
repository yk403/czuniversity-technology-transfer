package com.itts.paymentservice.controller.ddxfjl;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/2
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/ddxfjl/v1")
@Api(tags = "订单消费记录后台管理")
public class DdxfjlAdminController {

    @ApiOperation("获取列表")
    @GetMapping("/list/")
    public ResponseUtil list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        return null;
    }

    @ApiOperation("获取详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        return null;
    }
}