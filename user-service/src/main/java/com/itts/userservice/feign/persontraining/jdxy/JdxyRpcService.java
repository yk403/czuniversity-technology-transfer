package com.itts.userservice.feign.persontraining.jdxy;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.request.jsxy.AddJdxyRequest;
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
public interface JdxyRpcService {

    /**
     * 添加基地学员
     */
    @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/xs/addUser")
    ResponseUtil addUsr(@RequestBody AddJdxyRequest addJdxyRequest,
                        @RequestHeader(name = "token") String token);
}