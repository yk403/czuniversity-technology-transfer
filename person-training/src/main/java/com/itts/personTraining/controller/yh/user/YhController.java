package com.itts.personTraining.controller.yh.user;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.userservice.UserFeignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/23
 * @Version: 1.0.0
 * @Description: 用户前台
 */
@RestController
@RequestMapping(BASE_URL + "/v1/yh")
@Api(value = "YhController", tags = "用户-门户")
public class YhController {

    @Autowired
    private UserFeignService userFeignService;
    /**
     * 获取当前登陆用户信息
     */
    @GetMapping("/get")
    public ResponseUtil get() {
        return userFeignService.get();
    }
}
