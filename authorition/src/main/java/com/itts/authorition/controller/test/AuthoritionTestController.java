package com.itts.authorition.controller.test;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.ResponseUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/19
 */

@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/test/authorition")
public class AuthoritionTestController {

    /**
     * 测试方法
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/test/")
    public ResponseUtil test() {

        return ResponseUtil.success("鉴权测试方法");
    }

    /**
     * 测试方法
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/test/user/get/")
   // @PreAuthorize("hasRole('USER')")
    public ResponseUtil testRole() {

        return ResponseUtil.success("校验角色");
    }
}