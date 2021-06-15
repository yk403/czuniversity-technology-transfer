package com.itts.personTraining.controller.sj.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.ks.KsService;
import com.itts.personTraining.service.sj.SjService;
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
 * @Description: 实践门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/sj")
@Api(value = "sjController", tags = "实践-门户")
public class SjController {
    @Autowired
    private SjService sjService;

    /**
     * 根据用户id查询实践信息
     *
     * @return
     */
    @GetMapping("/getByYhId")
    @ApiOperation(value = "根据用户id查询实践信息")
    public ResponseUtil getByYhId() {
        return ResponseUtil.success(sjService.findByYhId());
    }
}
