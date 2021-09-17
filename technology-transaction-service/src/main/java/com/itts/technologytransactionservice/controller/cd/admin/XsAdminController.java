package com.itts.technologytransactionservice.controller.cd.admin;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.persontraining.XsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/jjr")
@Api(value = "经纪人下拉框")
public class XsAdminController {


    @Resource
    private XsService xsService;

    @GetMapping("/rpc/list")
    public ResponseUtil findByPage(@RequestParam(value = "xslbmc", required = false) String xslbmc,
                            @RequestParam(value = "fjjgId", required = false) Long fjjgId){
        ResponseUtil byXslbmc = xsService.findByXslbmc(xslbmc, fjjgId);
        return byXslbmc;
    };
}
