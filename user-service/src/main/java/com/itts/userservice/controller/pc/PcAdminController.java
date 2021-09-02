package com.itts.userservice.controller.pc;

import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.feign.persontraining.pc.PcRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;

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

    /**
     * 根据教育类型查询批次信息
     * @param jylx
     * @return
     */
    @GetMapping("/getByJylx/")
    @ApiOperation(value = "根据教育类型查询批次信息")
    public ResponseUtil getByJylx(@RequestParam(value = "jylx")String jylx,
                                  @RequestParam(value = "fjjgId") Long fjjgId){

        return pcRpcService.getByJylx(jylx,fjjgId);
    }
}
