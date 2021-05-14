package com.itts.personTraining.controller.ksExp.admin;


import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.service.ksExp.KsExpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * <p>
 * 考试扩展表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-05-13
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/ksExp")
@Api(value = "KsExpAdminController", tags = "考试扩展后台管理")
public class KsExpAdminController {

    @Autowired
    private KsExpService ksExpService;

    /**
     * 根据考试id查询考试扩展信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据考试id查询考试扩展信息")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(ksExpService.get(id));
    }
}

