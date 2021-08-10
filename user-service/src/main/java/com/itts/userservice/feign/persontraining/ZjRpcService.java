package com.itts.userservice.feign.persontraining;

import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.zj.Zj;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "person-training-service")
public interface ZjRpcService {


    @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/zj/addZj")
    ResponseUtil add(@RequestBody Zj zj) throws WebException;
}
