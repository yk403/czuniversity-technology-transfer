package com.itts.userservice.feign.persontraining.zj;

import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.zj.Zj;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "person-training-service")
public interface ZjRpcService {


    @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/zj/addZj")
    ResponseUtil add(@RequestBody Zj zj) throws WebException;

    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/zj//getByXmDh")
    ResponseUtil get(@RequestParam(value = "xm", required = false) String xm,
                                  @RequestParam(value = "dh", required = false) String dh,
                                  @RequestParam(value = "yhId", required = false) Long yhId);


    @PutMapping(SystemConstant.ADMIN_BASE_URL + "/v1/zj/updateZj")
    ResponseUtil update(@RequestBody Zj zj) throws WebException;
}
