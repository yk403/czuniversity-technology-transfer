package com.itts.paymentservice.controller.test;

import com.itts.common.utils.common.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：lym
 * @Description：测试controller
 * @Date: 2021/3/12
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/get/")
    public ResponseUtil get() {

        return ResponseUtil.success("test");
    }
}