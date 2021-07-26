package com.itts.userservice.feign.persontraining.gngl;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.request.gngl.AddGnglRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/26
 */
@FeignClient(value = "person-training-service")
public interface GnglFeignService {

    /**
     * 新增
     */
    @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/gngl/add/")
    ResponseUtil add(@RequestBody AddGnglRequest gngl);


}
