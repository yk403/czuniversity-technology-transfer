package com.itts.userservice.controller.test;

import cn.hutool.json.JSONUtil;
import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.RedisU;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/test")
public class Test {
    @Resource
    private RedisU redisU;

    @GetMapping("/test")
    public Boolean testt(@RequestParam(value = "key")String key,@RequestParam(value = "value")String value) {
        Boolean set = redisU.set(key, value);

        return set;
    }
}
