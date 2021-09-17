package com.itts.technologytransactionservice.controller.cd.admin;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.SjzdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private SjzdService sjzdFeignService;
    /**
     * 获取列表
     */
    @GetMapping("/findList/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findList(@RequestParam(value = "xtlb", required = false) String xtlb,
                         @RequestParam(value = "mklx", required = false) String mklx,
                         @RequestParam(value = "ssmk", required = false) String ssmk){
        return sjzdFeignService.getNotList(xtlb, mklx, ssmk);
    }
}
