package com.itts.personTraining.controller.jg.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.userservice.GroupFeignService;
import com.itts.personTraining.service.jg.JgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/8/10
 * @Version: 1.0.0
 * @Description: 机构管理前台
 */
@RestController
@RequestMapping(BASE_URL + "/v1/jggl")
@Api(value = "JgController", tags = "机构前台管理")
public class JgController {
    @Autowired
    private JgService jgService;

    @Autowired
    private GroupFeignService groupFeignService;

    /**
     * 获取基地列表（分基地、总基地）
     */
    @ApiOperation(value = "获取基地列表（分基地、总基地）")
    @GetMapping("/find/base/list/")
    public ResponseUtil findBaseList(@RequestParam(value = "jgId", required = false) Long jgId) {

        ResponseUtil response = groupFeignService.findBaseList(jgId);
        return response;
    }
}
