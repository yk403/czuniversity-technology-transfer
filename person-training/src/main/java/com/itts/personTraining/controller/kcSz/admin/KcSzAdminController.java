package com.itts.personTraining.controller.kcSz.admin;


import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.kcSz.KcSzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * <p>
 * 课程师资关系表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-25
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/kcSz")
@Api(value = "KcSzAdminController", tags = "课程师资关系后台管理")
public class KcSzAdminController {

    @Autowired
    private KcSzService kcSzService;

    /**
     * 根据kcId查询师资ids
     *
     * @param kcId
     * @return
     */
    @GetMapping("/get/{kcId}")
    @ApiOperation(value = "根据kcId查询师资ids")
    public ResponseUtil get(@PathVariable("kcId") Long kcId) {
        return ResponseUtil.success(kcSzService.get(kcId));
    }
}

