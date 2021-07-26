package com.itts.userservice.feign.persontraining.rmdt;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.request.rmdt.AddRmdtRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "person-training-service")
public interface RmdtFeignService {
     @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/rmdt/add/")
     ResponseUtil add(@RequestBody AddRmdtRequest rmdt);
}
