package com.itts.userservice.feign.persontraining.stgl;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.request.stgl.AddStglRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping("/add/")
    ResponseUtil add(@RequestBody AddStglRequest stgl);

}
