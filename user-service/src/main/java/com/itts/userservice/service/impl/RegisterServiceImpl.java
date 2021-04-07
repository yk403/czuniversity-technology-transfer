package com.itts.userservice.service.impl;

import com.itts.userservice.mapper.yh.YhJsGlMapper;
import com.itts.userservice.mapper.yh.YhMapper;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.model.yh.YhJsGl;
import com.itts.userservice.request.RegisterRequest;
import com.itts.userservice.service.RegisterService;
import com.itts.userservice.vo.RegisterYhVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description：用户注册Service实现
 * @Author：lym
 * @Date: 2021/3/26
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private YhMapper yhMapper;

    @Autowired
    private YhJsGlMapper yhJsGlMapper;

    /**
     * 注册用户
     *
     * @param request 用户注册信息
     * @param js      用户注册默认角色
     * @return
     * @author liuyingming
     */
    @Override
    public RegisterYhVO register(RegisterRequest request, Js js) {

        Date now = new Date();

        Yh yh = new Yh();
        yh.setYhm(request.getUserName());
        yh.setMm(new BCryptPasswordEncoder().encode(request.getPassword()));
        yh.setLxdh(request.getMobile());
        yh.setCjsj(now);
        yh.setGxsj(now);
        yhMapper.insert(yh);

        YhJsGl yhJsGl = new YhJsGl();
        yhJsGl.setYhId(yh.getId());
        yhJsGl.setJsId(js.getId());
        yhJsGl.setCjsj(now);
        yhJsGl.setGxsj(now);
        yhJsGlMapper.insert(yhJsGl);

        RegisterYhVO registerYhVO = new RegisterYhVO();
        BeanUtils.copyProperties(yh, registerYhVO);

        return registerYhVO;
    }
}