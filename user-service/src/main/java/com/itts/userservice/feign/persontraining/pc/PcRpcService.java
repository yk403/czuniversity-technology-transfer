package com.itts.userservice.feign.persontraining.pc;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "person-training-service")
public interface PcRpcService {

    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/pc/getAll")
    ResponseUtil getAll();
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/pc/getByJylx/")
    ResponseUtil getByJylx(@RequestParam(value = "jylx")String jylx,
                           @RequestParam(value = "fjjgId") Long fjjgId);

    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/pc/get/{id}")
    ResponseUtil getByOne(@PathVariable("id")Long id);
}
