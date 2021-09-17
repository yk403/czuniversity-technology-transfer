package com.itts.technologytransactionservice.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-09-17
 */
@RestController
@RequestMapping(BASE_URL + "/v1/jsMsg")
@Api(tags = "技术留言")
public class JsMsgController {

}
