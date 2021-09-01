package com.itts.userservice.feign.persontraining.lmgl;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.lmgl.Lmgl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "person-training-service")
public interface LmglRpcService {
    /**
     * 查询
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/lmgl/getOne")
    ResponseUtil getOne(@RequestParam(value = "jgId") Long jgId,
                               @RequestParam(value = "lmbm") String lmbm);

    /**
     * 新增
     */
    @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/lmgl/add/")
    ResponseUtil add(@RequestBody Lmgl lmgl);
}
