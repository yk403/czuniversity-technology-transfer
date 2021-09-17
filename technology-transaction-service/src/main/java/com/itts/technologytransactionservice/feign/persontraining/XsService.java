package com.itts.technologytransactionservice.feign.persontraining;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "person-training-service")
public interface XsService {

    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xs/rpc/list")
    ResponseUtil findByXslbmc(@RequestParam(value = "xslbmc", required = false) String xslbmc,
                                   @RequestParam(value = "fjjgId", required = false) Long fjjgId);
}
