package com.itts.userservice.feign.persontraining.stgl;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.request.stgl.AddStglRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/23
 */
@FeignClient(value = "person-training-service")
public interface StglFeignService {

    /**
     * 新增
     */
    @PostMapping(ADMIN_BASE_URL +"/v1/stgl/add/")
    ResponseUtil add(@RequestBody AddStglRequest stgl);

}
