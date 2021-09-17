package com.itts.technologytransactionservice.feign.userservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description：数据字典
 * @Author：lym
 * @Date: 2021/5/6
 */
@FeignClient(value = "user-service")
public interface SjzdService {

    /**
     * 获取数据字典
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/sjzd/list/")
    ResponseUtil getList(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @ApiParam(value = "所属模块") @RequestParam(value = "model", required = false) String model,
                         @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                         @ApiParam(value = "字典项类型") @RequestParam(value = "dictionary", required = false) String dictionary,
                         @ApiParam(value = "字典编码") @RequestParam(value = "zdbm", required = false) String zdbm,
                         @ApiParam(value = "父级字典ID") @RequestParam(value = "parentId", required = false) Long parentId);
    /**
     * 获取列表
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/sjzd/getList/")
    ResponseUtil getNotList(@RequestParam(value = "xtlb", required = false) String xtlb,
                         @RequestParam(value = "mklx", required = false) String mklx,
                         @RequestParam(value = "ssmk", required = false) String ssmk);
}
