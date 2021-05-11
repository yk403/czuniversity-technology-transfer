package com.itts.personTraining.controller.kc.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.kc.KcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.itts.common.constant.SystemConstant.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/v1/kc")
@Api(value = "KcController", tags = "课程前台")
public class KcController {


    @Autowired
    private KcService kcService;
    /**
     * 查询所有课程
     */
    @GetMapping("/getAll/")
    @ApiOperation(value = "查询所有课程")
    public ResponseUtil getAll() {
        return ResponseUtil.success(kcService.getAll());
    }
}
