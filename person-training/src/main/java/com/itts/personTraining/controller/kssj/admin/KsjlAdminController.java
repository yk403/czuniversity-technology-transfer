package com.itts.personTraining.controller.kssj.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kssj.Ksjl;
import com.itts.personTraining.service.kssj.KsjlService;
import com.itts.personTraining.vo.kssj.GetKsjlVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 考试记录 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-27
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/ksjl")
@Api(tags = "考试记录后台管理")
public class KsjlAdminController {

    @Autowired
    private KsjlService ksjlService;

    @ApiOperation(value = "获取列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "试卷名称") @RequestParam(value = "sjmc", required = false) String sjmc,
                             @ApiParam(value = "学生编号") @RequestParam(value = "xsbm", required = false) String xsbm,
                             @ApiParam(value = "学生姓名") @RequestParam(value = "xsmc", required = false) String xsmc) {

        PageHelper.startPage(pageNum, pageSize);

        List<Ksjl> list = ksjlService.list(new QueryWrapper<Ksjl>().like(StringUtils.isNotBlank(sjmc), "sjmc", sjmc)
                .eq(StringUtils.isNotBlank(xsbm), "xsbm", xsbm)
                .like(StringUtils.isNotBlank(xsmc), "xsmc", xsmc));

        PageInfo<Ksjl> pageInfo = new PageInfo<>(list);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation(value = "查看详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        GetKsjlVO ksjl = ksjlService.get(id);

        if (ksjl == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(ksjl);
    }
}

