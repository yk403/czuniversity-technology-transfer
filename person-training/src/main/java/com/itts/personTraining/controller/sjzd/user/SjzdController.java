package com.itts.personTraining.controller.sjzd.user;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.userservice.SjzdFeignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/8/13
 * @Version: 1.0.0
 * @Description: 数据字典-前台
 */
@RestController
@RequestMapping(BASE_URL + "/v1/sjzd")
@Api(value = "PcAdminController", tags = "数据字典-门户")
public class SjzdController {

    @Autowired
    private SjzdFeignService sjzdFeignService;
    /**
     * 获取列表
     */
    @GetMapping("/v1//findList/")
    public ResponseUtil findList(@RequestParam(value = "xtlb", required = false) String xtlb,
                         @RequestParam(value = "mklx", required = false) String mklx,
                         @RequestParam(value = "ssmk", required = false) String ssmk){
        return sjzdFeignService.findList(xtlb, mklx, ssmk);
    }
}
