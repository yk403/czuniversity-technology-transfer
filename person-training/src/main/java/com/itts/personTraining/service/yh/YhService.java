package com.itts.personTraining.service.yh;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.vo.yh.RpcAddYhRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @author Austin
 * @since 2021-05-11
 */
@FeignClient(value = "user-service")
public interface YhService {
    /**
     * 新增用户
     */
    @PostMapping(ADMIN_BASE_URL + "/v1/yh/rpc/add/")
    @ApiOperation(value = "新增")
    ResponseUtil rpcAdd(@RequestBody Yh yh,@RequestHeader(name = "token") String token) throws WebException;

    @GetMapping(ADMIN_BASE_URL + "/v1/yh/get/by/phone/")
    @ApiOperation(value = "通过用户手机号查询")
    ResponseUtil getByPhone(@RequestParam("phone") String phone,@RequestHeader(name = "token") String token);
    /**
     * 获取详情
     *
     * @param id
     * @author fl
     */
    @GetMapping(ADMIN_BASE_URL + "/v1/yh/getBy/{id}")
    ResponseUtil getById(@PathVariable("id") Long id);
    /**
     * 更新
     *
     * @author fl
     */
    @PutMapping(ADMIN_BASE_URL + "/v1/yh/updateYh")
    ResponseUtil updateYh(@RequestBody Yh yh);
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

    /**
     * 新增用户(前台)
     */
    @ApiOperation(value = "新增用户(前台)")
    @PostMapping(BASE_URL + "/v1/yh/rpc/add/")
    ResponseUtil rpcAdd(@RequestBody RpcAddYhRequest yh) throws WebException;

}
