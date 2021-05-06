package com.itts.personTraining.controller.jwgl.admin;

import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.JwglDTO;
import com.itts.personTraining.service.xs.XsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL+"/v1/jwgl")
@Api(value = "JwglAdminController", tags = "教务管理")
public class JwglAdminController {
    @Autowired
    private XsService xsService;

    @GetMapping("/list/")
    @ApiOperation(value = "教务管理列表")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "string", required = false) String string,
                                @RequestParam(value = "pcId", required = false) Long pcId,
                                @RequestParam(value = "yx", required = false) String yx){
        PageInfo<JwglDTO> jwglByPage = xsService.findJwglByPage(pageNum, pageSize, string, yx ,pcId);
        return ResponseUtil.success(jwglByPage);
    }
}
