package com.itts.personTraining.controller.tz.admin;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * <p>
 * 通知表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-06-21
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/tz")
@Api(value = "TzAdminController", tags = "通知后台管理")
public class TzAdminController {



}

