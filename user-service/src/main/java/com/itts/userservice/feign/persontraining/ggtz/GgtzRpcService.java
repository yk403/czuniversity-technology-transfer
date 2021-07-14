package com.itts.userservice.feign.persontraining.ggtz;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.ggtz.Ggtz;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * fuli
 */
@FeignClient(value = "person-training-service")
public interface GgtzRpcService {

    /**
     * 查询
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/ggtz/list/")
    ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @RequestParam(value = "jgId") Long jgId,
                         @RequestParam(value = "zt",required = false) String zt,
                         @RequestParam(value = "lx",required = false) String lx);
    /**
     * 新增
     */
    @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/ggtz/add/")
    ResponseUtil add(@RequestBody Ggtz ggtz);
    /**
     * 更新
     */
    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/ggtz/update/")
    ResponseUtil update(@RequestBody Ggtz ggtz);
    /**
     * 发布
     */
    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/ggtz/release/{id}")
    ResponseUtil release(@PathVariable("id") Long id);
    /**
     * 停用
     */
    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/ggtz/stop/{id}")
    ResponseUtil stop(@PathVariable("id") Long id);
    /**
     * 查询
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/ggtz/get/{id}")
    ResponseUtil get(@PathVariable("id") Long id);
    /**
     * 删除
     */
    @DeleteMapping(SystemConstant.ADMIN_BASE_URL + "/v1/ggtz/delete/{id}")
    ResponseUtil delete(@PathVariable("id") Long id);
}
