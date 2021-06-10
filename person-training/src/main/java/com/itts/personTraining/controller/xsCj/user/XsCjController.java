package com.itts.personTraining.controller.xsCj.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.ks.KsService;
import com.itts.personTraining.service.xsCj.XsCjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/8
 * @Version: 1.0.0
 * @Description: 学生成绩门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/xsCj")
@Api(value = "XsCjController", tags = "学生成绩-门户")
public class XsCjController {

    @Autowired
    private XsCjService xsCjService;

    /**
     * 根据用户id查询学生成绩详情
     *
     * @return
     */
    @GetMapping("/getByYhId")
    @ApiOperation(value = "根据用户id查询学生成绩详情")
    public ResponseUtil getByYhId() {
        return ResponseUtil.success(xsCjService.getByYhId());
    }
}
