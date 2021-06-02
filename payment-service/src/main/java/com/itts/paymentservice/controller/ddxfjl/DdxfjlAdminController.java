package com.itts.paymentservice.controller.ddxfjl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.service.DdxfjlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/2
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/ddxfjl/v1")
@Api(tags = "订单消费记录后台管理")
public class DdxfjlAdminController {
    
    @Autowired
    private DdxfjlService ddxfjlService;

    @ApiOperation("获取列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "订单编号") @RequestParam(value = "ddbh", required = false) String ddbh,
                             @ApiParam(value = "联系电话") @RequestParam(value = "lxdh", required = false) String lxdh) {

        PageHelper.startPage(pageNum, pageSize);

        List<Ddxfjl> list = ddxfjlService.list(new QueryWrapper<Ddxfjl>()
                .eq(StringUtils.isNotBlank(ddbh), "bh", ddbh)
                .eq(StringUtils.isNotBlank(lxdh), "lxdh", lxdh));

        PageInfo pageInfo = new PageInfo(list);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation("获取详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        return null;
    }
}