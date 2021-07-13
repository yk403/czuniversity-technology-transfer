package com.itts.webcrawler.controller;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.webcrawler.service.TJsspService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/*
 *@Auther: yukai
 *@Date: 2021/07/13/10:33
 *@Desription:
 */
@RestController
@RequestMapping(BASE_URL+"/v1/TJssp")
@Api(value = "TJsspController", tags = "爬虫页面展示")
public class TJsspController {
    @Autowired
    private TJsspService tJsspService;
    /**
    * @Description: 获取列表
    * @Param: [params]
    * @return: com.itts.common.utils.common.ResponseUtil
    * @Author: yukai
    * @Date: 2021/7/13
    */
    @ApiOperation(value = "获取列表")
    @PostMapping("/list")
    public ResponseUtil find(@RequestBody Map<String, Object> params) {
//return null;
        return ResponseUtil.success(tJsspService.findTJssp(params));
    }
    /**
    * @Description: 根据id查询
    * @Param: [id]
    * @return: com.itts.common.utils.common.ResponseUtil
    * @Author: yukai
    * @Date: 2021/7/13
    */
    @GetMapping("/getById/{id}")
    public ResponseUtil getById(@PathVariable("id") Long id) {
        return ResponseUtil.success(tJsspService.getById(id));
    }
}
