package com.itts.technologytransactionservice.controller.test;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.SjzdService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/6
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private SjzdService sjzdService;

    @GetMapping("/feign/")
    public ResponseUtil feignTest(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                  @ApiParam(value = "所属模块") @RequestParam(value = "model", required = false) String model,
                                  @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                                  @ApiParam(value = "字典项类型") @RequestParam(value = "dictionary", required = false) String dictionary,
                                  @ApiParam(value = "字典编码") @RequestParam(value = "zdbm", required = false) String zdbm,
                                  @ApiParam(value = "父级字典ID") @RequestParam(value = "parentId", required = false) Long parentId){

        ResponseUtil result = sjzdService.getList(pageNum,pageSize,model,systemType,dictionary,zdbm,parentId);

        return result;
    }
}