package com.itts.personTraining.controller.pc.user;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.service.pc.PcService;
import com.itts.personTraining.service.sjzd.SjzdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/21
 * @Version: 1.0.0
 * @Description: 批次前台
 */
@RestController
@RequestMapping(BASE_URL + "/v1/pc")
@Api(value = "PcAdminController", tags = "批次-门户")
public class PcController {

    @Autowired
    private SjzdService sjzdService;
    @Autowired
    private PcService pcService;

    /**
     * 根据字典项类型查询类型
     * @param pageNum
     * @param pageSize
     * @param model
     * @param systemType
     * @param dictionary
     * @param zdbm
     * @param parentId
     * @param request
     * @return
     */
    @GetMapping("/getByDictionary")
    @ApiOperation(value = "根据字典项类型查询类型")
    public ResponseUtil getList(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @ApiParam(value = "模块类型") @RequestParam(value = "model", required = false) String model,
                                @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                                @ApiParam(value = "所属模块编码") @RequestParam(value = "dictionary",required = false) String dictionary,
                                @ApiParam(value = "字典编码") @RequestParam(value = "zdbm", required = false) String zdbm,
                                @ApiParam(value = "父级字典ID") @RequestParam(value = "parentId", required = false) Long parentId,
                                @ApiParam(value = "父级字典Code") @RequestParam(value = "parentCode", required = false) String parentCode,
                                HttpServletRequest request){

        return sjzdService.getList(pageNum, pageSize, model, systemType, dictionary, zdbm, parentId, parentCode, request.getHeader("token"));
    }

    /**
     * 根据用户查询批次列表
     * @return
     */
    @GetMapping("/findByYh")
    @ApiOperation(value = "根据用户查询批次列表")
    public ResponseUtil findByYh(){
        List<Pc> pcList = pcService.findByYh();
        return ResponseUtil.success(pcList);
    }

}
