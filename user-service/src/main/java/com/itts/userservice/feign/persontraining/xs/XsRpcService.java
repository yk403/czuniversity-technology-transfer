package com.itts.userservice.feign.persontraining.xs;

import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.xs.Xs;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "person-training-service")
public interface XsRpcService {


    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xs/getByCondition")
    ResponseUtil get(@RequestParam(value = "xh",required = false) String xh,
                                          @RequestParam(value = "lxdh",required = false) String lxdh,
                                          @RequestParam(value = "yhId",required = false) Long yhId);


    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xs/updateXs")
    ResponseUtil update(@RequestBody Xs xs) throws WebException;
}
