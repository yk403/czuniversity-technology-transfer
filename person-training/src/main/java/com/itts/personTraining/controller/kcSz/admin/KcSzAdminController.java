package com.itts.personTraining.controller.kcSz.admin;


import io.swagger.annotations.Api;
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

}

