package com.itts.technologytransactionservice.controller;

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

import javax.servlet.http.HttpServletRequest;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/*
 *@Auther: yukai
 *@Date: 2021/06/02/16:20
 *@Desription:
 */
@RequestMapping(BASE_URL+"/v1/sjzd")
@Api(value = "SjzdController", tags = "类别领域字典服务")
@RestController
public class SjzdController {
    @Autowired
    private SjzdService sjzdService;
    /**
    * @Description:
    * @Param:
    * @return:
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

}
