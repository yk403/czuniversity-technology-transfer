package com.itts.userservice.feign.persontraining.sz;

import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.sz.Sz;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "person-training-service")
public interface SzRpcService {


    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/Sz/getByCondition")
    ResponseUtil get(@RequestParam(value = "dsbh",required = false) String dsbh,
                                          @RequestParam(value = "xb",required = false) String xb,
                                          @RequestParam(value = "yhId",required = false) Long yhId,
                                          @RequestParam(value = "groupId",required = false) Long groupId);



    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/Sz/updateSz")
    ResponseUtil update(@RequestBody Sz sz) throws WebException;
}
