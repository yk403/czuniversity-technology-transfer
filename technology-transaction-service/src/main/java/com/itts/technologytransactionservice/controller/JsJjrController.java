package com.itts.technologytransactionservice.controller;


import com.github.pagehelper.PageInfo;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.JsJjrDTO;
import com.itts.technologytransactionservice.service.JsJjrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-09-17
 */
@RestController
@RequestMapping(BASE_URL + "/v1/jsJjr")
@Api(tags = "技术经纪人管理")
public class JsJjrController {
    @Resource
    private JsJjrService jsJjrService;

    @GetMapping("/list/")
    @ApiOperation(value = "经纪人列表")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "xslbmcArr",required = false)String xslbmcArr,
                                @RequestParam(value = "fjjgId",required = false)Long jgId,
                                @RequestParam(value = "zsxm",required = false)String zsxm){
        xslbmcArr = "C01,C02,C03";
        PageInfo<JsJjrDTO> page = jsJjrService.findPage(pageNum, pageSize, xslbmcArr, jgId, zsxm);
        return ResponseUtil.success(page);
    }
}

