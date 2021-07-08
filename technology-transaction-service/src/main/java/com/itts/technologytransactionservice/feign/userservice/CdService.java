package com.itts.technologytransactionservice.feign.userservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 *@Auther: yukai
 *@Date: 2021/07/07/10:25
 *@Desription: 菜单管理
 */
@FeignClient(value = "user-service")
public interface CdService {
    /**
     * 获取数据字典
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/cd/tree/")
    ResponseUtil findByTree(@ApiParam(value = "菜单ID(可不填写，默认查询所有)") @RequestParam(value = "id", required = false) Long id,
                            @ApiParam(value = "类型(不填写查询所有)：in - 内部系统；out - 外部系统") @RequestParam(value = "type", required = false) String systemType);
}
