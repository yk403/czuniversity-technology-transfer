package com.itts.personTraining.feign.userservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.itts.common.constant.SystemConstant.BASE_URL;

@FeignClient("user-service")
public interface SjzdFeignService {



    /**
     * 获取列表
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/sjzd/getList/")
    ResponseUtil getList(@RequestParam(value = "xtlb", required = false) String xtlb,
                                @RequestParam(value = "mklx", required = false) String mklx,
                                @RequestParam(value = "ssmk", required = false) String ssmk);

    /**
     * 获取列表
     */
    @GetMapping(BASE_URL + "/v1/sjzd/findList/")
    @ApiOperation(value = "获取列表")
    ResponseUtil findList(@RequestParam(value = "xtlb", required = false) String xtlb,
                          @RequestParam(value = "mklx", required = false) String mklx,
                          @RequestParam(value = "ssmk", required = false) String ssmk);
}
