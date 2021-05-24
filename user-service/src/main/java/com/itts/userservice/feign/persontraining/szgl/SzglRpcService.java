package com.itts.userservice.feign.persontraining.szgl;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.request.szgl.AddSzglRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/19
 */
@FeignClient(value = "person-training-service")
public interface SzglRpcService {

    /**
     * 新增师资管理
     */
    @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/sz/addSz")
    ResponseUtil addSzgl(@RequestBody AddSzglRequest addSzglRequest,
                         @RequestHeader(name = "token") String token);
}