package com.itts.personTraining.controller.jg.admin;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.jg.JgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/7/14
 * @Version: 1.0.0
 * @Description: 机构控制层
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/jggl")
@Api(value = "JgAdminController", tags = "机构后台管理")
public class JgAdminController {

    @Autowired
    private JgService jgService;

    /**
     * 获取机构列表
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取机构列表")
    public ResponseUtil getlist(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @RequestParam(value = "jgbm",required = false) String jgbm,
                         @RequestHeader(name = "token") String token) {
        return jgService.getlist(pageNum,pageSize,jgbm,token);
    }

    /**
     * 获取机构树
     */
    @GetMapping("/tree/")
    @ApiOperation(value = "获取机构树")
    public ResponseUtil findJgglVO(@RequestParam(value = "jgbm", required = false) String jgbm) {
        return jgService.findJgglVO(jgbm);
    }

    /**
     * 获取详情
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return jgService.get(id);
    }

}
