package com.itts.personTraining.controller.sz.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xs.XsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/23
 * @Version: 1.0.0
 * @Description: 师资门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/sz")
@Api(value = "SzController", tags = "师资-门户")
public class SzController {
    @Autowired
    private SzService szService;

    /**
     * 查询师资综合信息
     *
     * @return
     */
    @GetMapping("/getByYhId")
    @ApiOperation(value = "查询师资综合信息")
    public ResponseUtil getByYhId() {
        return ResponseUtil.success(szService.getByYhId());
    }

    /**
     * 根据机构编号查询师资信息
     */
    @GetMapping("/getByJgBh")
    @ApiOperation(value = "根据机构编号查询师资信息")
    public ResponseUtil getByJgBh(@RequestParam("jgCode") String jgCode) {
        return ResponseUtil.success(szService.getByJgBh(jgCode));
    }
}
