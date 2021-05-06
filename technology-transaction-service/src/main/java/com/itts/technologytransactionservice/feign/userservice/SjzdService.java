package com.itts.technologytransactionservice.feign.userservice;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

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
    ResponseUtil list(Integer pageNum, Integer pageSize, String model, String systemType, String diction);

}
