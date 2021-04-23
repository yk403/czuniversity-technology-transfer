package com.itts.personTraining.controller.xy.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.pk.Pk;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;

/**
 * <p>
 * 学院表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-23
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/xy")
@Api(value = "XyAdminController", tags = "学院后台管理")
public class XyAdminController {


}

