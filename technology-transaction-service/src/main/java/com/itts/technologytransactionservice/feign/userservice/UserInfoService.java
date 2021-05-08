package com.itts.technologytransactionservice.feign.userservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service")
public interface UserInfoService {
    /**
     * 获取数据字典
     */
    @GetMapping(SystemConstant.BASE_URL + "/v1/yh/get/")
    ResponseUtil get();
}
