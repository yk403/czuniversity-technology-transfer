package com.itts.personTraining.controller.sj.user;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/8
 * @Version: 1.0.0
 * @Description: 实践门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/sj")
@Api(value = "sjController", tags = "实践-门户")
public class SjController {
}
