package com.itts.technologytransactionservice.controller.cd.admin;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.CdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/*
 *@Auther: yukai
 *@Date: 2021/07/07/10:15
 *@Desription:
 */
@RequestMapping(ADMIN_BASE_URL+"/v1/cd")
@Api(tags = "菜单控制")
@RestController
public class CdAdminController {
    @Autowired
    private CdService cdService;
    @GetMapping("/tree/")
    @ApiOperation(value = "通过ID获取当前菜单及其子菜单（树形）")
    public ResponseUtil findByTree(@ApiParam(value = "菜单ID(可不填写，默认查询所有)") @RequestParam(value = "id", required = false) Long id,
                                @ApiParam(value = "类型(不填写查询所有)：in - 内部系统；out - 外部系统") @RequestParam(value = "type", required = false) String systemType) {
        return cdService.findByTree(id, systemType);
    }
}
