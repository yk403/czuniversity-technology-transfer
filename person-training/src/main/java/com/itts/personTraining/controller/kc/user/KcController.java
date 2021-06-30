package com.itts.personTraining.controller.kc.user;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.kc.KcService;
import com.itts.personTraining.service.kcsl.KcsjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/kc")
@Api(tags = "课程")
public class KcController {

    @Autowired
    private KcService kcService;

    /**
     * 根据用户id查询培养计划列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据用户id查询培养计划列表")
    public ResponseUtil findByYh(@RequestParam(value = "pcId",required = false) Long pcId) {
        return ResponseUtil.success(kcService.findByYh(pcId));
    }
}
