package com.itts.userservice.service;

import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.request.RegisterRequest;
import com.itts.userservice.vo.RegisterYhVO;

/**
 * @Description：用户注册Service
 * @Author：lym
 * @Date: 2021/3/26
 */
public interface RegisterService {

    /**
     * 注册用户
     *
     * @param request 用户注册信息
     * @param js 用户注册默认角色
     * @return
     * @author liuyingming
     */
    RegisterYhVO register(RegisterRequest request, Js js);

}
