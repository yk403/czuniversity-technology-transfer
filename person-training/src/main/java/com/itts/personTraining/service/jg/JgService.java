package com.itts.personTraining.service.jg;


import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * @author Austin
 * @since 2021-05-11
 */
@FeignClient(value = "user-service")
public interface JgService {
    /**
     * 获取机构列表
     */
    @GetMapping(ADMIN_BASE_URL + "/v1/jggl/list/")
    @ApiOperation(value = "获取机构列表")
    ResponseUtil getlist(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "jgbm",required = false) String jgbm);
}
