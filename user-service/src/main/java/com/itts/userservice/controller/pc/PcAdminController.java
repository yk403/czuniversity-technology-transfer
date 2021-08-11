package com.itts.userservice.controller.pc;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.feign.persontraining.pc.PcRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL+"/v1/pc")
@Api(value = "PcAdminController", tags = "批次")
public class PcAdminController {

    @Resource
    private PcRpcService pcRpcService;

    /**
     * 获取所有批次详情
     * @return
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有批次详情")
    public ResponseUtil getAll(){
        return pcRpcService.getAll();
    }
}
