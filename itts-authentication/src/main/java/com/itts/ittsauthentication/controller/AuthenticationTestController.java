package com.itts.ittsauthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/27
 */
@RequestMapping("/api")
@RestController
public class AuthenticationTestController {

    @GetMapping("/test/")
    public String test(){
        return "test";
    }

    @GetMapping("/register/v1/")
    public String register(){
        return "register";
    }
}