package com.itts.userservice.feign.persontraining.pk;


import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.itts.common.constant.SystemConstant.BASE_URL;

@FeignClient(value = "person-training-service")
public interface KcRpcService {
    @GetMapping(BASE_URL + "/v1/kc/findByXylx")
    ResponseUtil findByPage(@RequestParam(value = "xylx", required = false) String xylx,
                            @RequestParam(value = "jylx", required = false) String jylx);
}
