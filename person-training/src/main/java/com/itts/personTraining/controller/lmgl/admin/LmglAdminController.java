package com.itts.personTraining.controller.lmgl.admin;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
@RestController
@RequestMapping(ADMIN_BASE_URL +"/v1/lmgl")
@Api(value = "LmglAdminController", tags = "栏目管理")
public class LmglAdminController {

}

