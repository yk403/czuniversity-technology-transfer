package com.itts.technologytransactionservice.controller.test;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.SjzdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/6
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private SjzdService sjzdService;

    @GetMapping("/feign/")
    public ResponseUtil feignTest(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                  @RequestParam String model, @RequestParam String systemType,
                                  @RequestParam String diction, HttpServletRequest request){

        ResponseUtil result = sjzdService.getList(pageNum, pageSize, model, systemType, diction, request.getHeader("token"));

        return result;
    }
}