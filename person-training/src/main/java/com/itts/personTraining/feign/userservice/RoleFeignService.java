package com.itts.personTraining.feign.userservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/8
 */
@FeignClient("user-service")
public interface RoleFeignService {

    @GetMapping(SystemConstant.BASE_URL + "/v1/js/get/by/user/")
    ResponseUtil getByUserId(@RequestParam("userId") Long userId);
}
