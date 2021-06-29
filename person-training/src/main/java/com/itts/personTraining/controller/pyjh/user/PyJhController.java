package com.itts.personTraining.controller.pyjh.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.pyjh.PyJhService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/28
 * @Version: 1.0.0
 * @Description:  培养计划门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/pyJh")
@Api(value = "PyJhController", tags = "培养计划-门户")
public class PyJhController {
    @Autowired
    private PyJhService pyJhService;

    /**
     * 根据用户id查询培养计划列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据用户id查询培养计划列表")
    public ResponseUtil findByYh() {
        return ResponseUtil.success(pyJhService.findByYh());
    }

}
