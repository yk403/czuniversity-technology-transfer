package com.itts.personTraining.controller.xsCj.user;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/8
 * @Version: 1.0.0
 * @Description: 学生成绩前台
 */
@RestController
@RequestMapping(BASE_URL + "/v1/xsCj")
@Api(value = "XsCjController", tags = "学生成绩前台")
public class XsCjController {
}
