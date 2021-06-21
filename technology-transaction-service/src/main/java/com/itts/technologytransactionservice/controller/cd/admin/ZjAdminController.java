package com.itts.technologytransactionservice.controller.cd.admin;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.persontraining.ZjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;

/*
 *@Auther: yukai
 *@Date: 2021/06/03/14:41
 *@Desription:
 */
@RequestMapping(ADMIN_BASE_URL+"/v1/zj")
@Api(value = "ZjAdminController", tags = "双创路演专家服务")
@RestController
public class ZjAdminController {
    @Autowired
    private ZjService zjService;
    /**
     * @Description:
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/2
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取专家列表")
    public ResponseUtil getList(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @ApiParam(value = "研究领域(与技术领域数据字典相关)") @RequestParam(value = "yjly", required = false) String yjly,
                                @ApiParam(value = "名称") @RequestParam(value = "name", required = false) String name) {
        return zjService.getList(pageNum, pageSize, yjly, name);
    }
}
