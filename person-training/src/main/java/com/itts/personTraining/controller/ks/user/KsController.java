package com.itts.personTraining.controller.ks.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.ks.KsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/8
 * @Version: 1.0.0
 * @Description: 考试前台
 */
@RestController
@RequestMapping(BASE_URL + "/v1/ks")
@Api(value = "KsController", tags = "考试前台")
public class KsController {

    @Autowired
    private KsService ksService;

    /**
     * 根据用户id查询考试详情
     *
     * @return
     */
    @GetMapping("/getByYhId")
    @ApiOperation(value = "根据用户id查询考试详情")
    public ResponseUtil getByYhId() {
        return ResponseUtil.success(ksService.getByYhId());
    }
}
