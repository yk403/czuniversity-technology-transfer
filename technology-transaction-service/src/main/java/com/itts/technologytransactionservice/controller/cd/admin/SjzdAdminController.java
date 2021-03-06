package com.itts.technologytransactionservice.controller.cd.admin;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.SjzdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/8/13
 * @Version: 1.0.0
 * @Description: 数据字典-前台
 */
@RestController
@RequestMapping(ADMIN_BASE_URL+"/v1/sjzd")
@Api(value = "SjzdController", tags = "数据字典")
public class SjzdAdminController {


    @Autowired
    private SjzdService sjzdService;
    /**
     * @Description:
     * @Param: [pageNum, pageSize, model, systemType, dictionary, request]
     * @return: com.itts.common.utils.common.ResponseUtil
     * @Author: yukai
     * @Date: 2021/6/2
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取类别领域信息")
    public ResponseUtil getList(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @ApiParam(value = "所属模块") @RequestParam(value = "model", required = false) String model,
                                @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                                @ApiParam(value = "字典项类型") @RequestParam(value = "dictionary", required = false) String dictionary,
                                @ApiParam(value = "字典编码") @RequestParam(value = "zdbm", required = false) String zdbm,
                                @ApiParam(value = "父级字典ID") @RequestParam(value = "parentId", required = false) Long parentId) {
        return sjzdService.getList(pageNum,pageSize,model,systemType,dictionary,zdbm,parentId);
    }
    /**
     * 获取列表
     */
    @GetMapping("/findList/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findList(@RequestParam(value = "xtlb", required = false) String xtlb,
                         @RequestParam(value = "mklx", required = false) String mklx,
                         @RequestParam(value = "ssmk", required = false) String ssmk){
        return sjzdService.getNotList(xtlb, mklx, ssmk);
    }
}
