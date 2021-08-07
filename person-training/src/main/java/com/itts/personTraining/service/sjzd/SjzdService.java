package com.itts.personTraining.service.sjzd;

import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * @Description：数据字典
 * @Author：lym
 * @Date: 2021/5/6
 */
@FeignClient(value = "user-service")
public interface SjzdService {

    /**
     * 获取数据字典
     * @param pageNum
     * @param pageSize
     * @param model
     * @param systemType
     * @param dictionary
     * @param zdbm
     * @param parentId
     * @param token
     * @return
     */
    @GetMapping(ADMIN_BASE_URL + "/v1/sjzd/list/")
    ResponseUtil getList(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @ApiParam(value = "所属模块") @RequestParam(value = "model", required = false) String model,
                         @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                         @ApiParam(value = "字典项类型") @RequestParam(value = "dictionary", required = false) String dictionary,
                         @ApiParam(value = "字典编码") @RequestParam(value = "zdbm", required = false) String zdbm,
                         @ApiParam(value = "父级字典ID") @RequestParam(value = "parentId", required = false) Long parentId,
                         @ApiParam(value = "父级字典Code") @RequestParam(value = "parentCode", required = false) String parentCode,
                         @RequestHeader(name = "token") String token);

}
