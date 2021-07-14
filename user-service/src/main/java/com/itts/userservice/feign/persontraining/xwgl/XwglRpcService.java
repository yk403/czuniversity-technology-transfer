package com.itts.userservice.feign.persontraining.xwgl;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.xwgl.Xwgl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(value = "person-training-service")
public interface XwglRpcService {

    /**
     * 查询
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xwgl/list/")
    ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @RequestParam(value = "jgId",required = false) Long jgId,
                         @RequestParam(value = "zt", required = false) String zt,
                         @RequestParam(value = "lx", required = false) String lx);
    /**
     * 新增
     */
    @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xwgl/add/")
    ResponseUtil add(@RequestBody Xwgl xwgl);
    /**
     * 更新
     */
    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xwgl/update/")
    ResponseUtil update(@RequestBody Xwgl xwgl);
    /**
     * 发布
     */
    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xwgl/release/{id}")
    ResponseUtil release(@PathVariable("id") Long id);
    /**
     * 发布
     */
    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xwgl/release/issueBatch/")
    ResponseUtil issueBatch(@RequestBody List<Long> ids);
    /**
     * 停用
     */
    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xwgl/stop/{id}")
    ResponseUtil stop(@PathVariable("id") Long id);
    /**
     * 查询
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xwgl/get/{id}")
    ResponseUtil get(@PathVariable("id") Long id);
    /**
     * 删除
     */
    @DeleteMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xwgl/delete/{id}")
    ResponseUtil delete(@PathVariable("id") Long id);
}
