package com.itts.personTraining.feign.userservice;

import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/19
 */
@FeignClient(value = "user-service")
public interface GroupFeignService {

    /**
     * 获取基地列表（分基地、总基地）
     */
    @GetMapping("/admin/api/v1/jggl/find/base/list/")
    ResponseUtil findBaseList(@RequestParam("jgId") Long jgId);

    /**
     * 通过编码获取机构信息
     */
    @GetMapping("/api/v1/jggl/get/by/code/")
    ResponseUtil getByCode(@RequestParam(value = "code") String code);

}
