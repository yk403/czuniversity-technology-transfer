package com.itts.personTraining.service.yh;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.yh.Yh;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(ADMIN_BASE_URL + "/v1/yh/get/by/phone/")
    @ApiOperation(value = "通过用户手机号查询")
    ResponseUtil getByPhone(@RequestParam("phone") String phone,@RequestHeader(name = "token") String token);

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping(ADMIN_BASE_URL + "/v1/yh/update/")
    @Transactional(rollbackFor = Exception.class)
    ResponseUtil update(@RequestBody Yh yh,@RequestHeader(name = "token") String token) throws WebException;

    /**
     * 通过用户编号查询用户
     * @param code
     * @return
     */
    @ApiOperation(value = "通过用户编号查询")
    @GetMapping(ADMIN_BASE_URL + "/v1/yh/get/by/code/")
    ResponseUtil getByCode(@ApiParam("用户编号") @RequestParam("code") String code,@RequestHeader(name = "token") String token);
}
