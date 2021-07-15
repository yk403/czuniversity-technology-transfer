package com.itts.userservice.controller.test.spzb;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.service.spzb.thirdparty.HuaWeiLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/15
 */
@RestController
@RequestMapping(value = SystemConstant.BASE_URL + "/test/spzb")
public class HuaWeiLiveTestController {

    @Autowired
    private HuaWeiLiveService huaWeiLiveService;

    @GetMapping("/get/token/")
    public ResponseUtil getToken(){

        huaWeiLiveService.getToken();
        return ResponseUtil.success();
    }
}