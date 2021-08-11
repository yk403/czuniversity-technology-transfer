package com.itts.personTraining.controller.zj.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.zj.ZjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/8/10
 * @Version: 1.0.0
 * @Description: 专家门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/zj")
@Api(value = "ZjController", tags = "专家-门户")
public class ZjController {

    @Autowired
    private ZjService zjService;

    /**
     * 查询专家综合信息
     *
     * @return
     */
    @GetMapping("/getByYhId")
    @ApiOperation(value = "查询专家综合信息")
    public ResponseUtil getByYhId() {
        return ResponseUtil.success(zjService.getByYhId());
    }

}
