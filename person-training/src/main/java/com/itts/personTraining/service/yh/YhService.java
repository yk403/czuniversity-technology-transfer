package com.itts.personTraining.service.yh;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.yh.Yh;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * @author Austin
 * @since 2021-05-11
 */
@FeignClient(value = "user-service")
public interface YhService {
    /**
     * 新增用户
     */
    @PostMapping(ADMIN_BASE_URL + "/v1/yh/add/")
    @ApiOperation(value = "新增")
    ResponseUtil add(@RequestBody Yh yh,@RequestHeader(name = "token") String token) throws WebException;
}
