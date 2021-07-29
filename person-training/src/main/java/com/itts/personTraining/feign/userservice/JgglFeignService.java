package com.itts.personTraining.feign.userservice;

import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * 机构管理远程调用
 */
@FeignClient(value = "user-service")
public interface JgglFeignService {
    /**
     *通过编码获取数据
     */
    @GetMapping(ADMIN_BASE_URL + "/v1/jggl/get/by/code/")
    ResponseUtil getByCode(@RequestParam("code") String code);
}
