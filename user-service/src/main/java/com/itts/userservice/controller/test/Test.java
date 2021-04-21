package com.itts.userservice.controller.test;

import cn.hutool.json.JSONUtil;
import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.RedisU;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/test")
public class Test {

    @GetMapping("/")
    public ResponseUtil test() throws UnknownHostException {

        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr);

        return ResponseUtil.success(addr);
    }
}
