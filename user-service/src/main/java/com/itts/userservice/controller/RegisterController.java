package com.itts.userservice.controller;

import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.request.RegisterRequest;
import com.itts.userservice.service.RegisterService;
import com.itts.userservice.service.js.JsService;
import com.itts.userservice.service.yh.YhService;
import com.itts.userservice.vo.RegisterYhVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/26
 */
@RequestMapping(SystemConstant.BASE_URL)
@RestController
@Api(tags = "注册管理")
public class RegisterController {

    @Autowired
    private YhService yhService;

    @Autowired
    private JsService jsService;

    @Autowired
    private RegisterService registerService;

    /**
     * 用户注册（门户端）
     *
     * @param
     * @return
     * @author liuyingming
     */
    @PostMapping("/register/")
    @ApiOperation(value = "用户注册")
    public ResponseUtil register(@RequestBody RegisterRequest request) {

        checkRegisterRequest(request);

        //验证当前用户名是否被注册
        Yh yh = yhService.getByUserName(request.getUserName());

        if (yh != null) {
            throw new WebException(ErrorCodeEnum.REGISTER_USERNAME_EXISTS_ERROR);
        }

        //获取门户系统默认角色
        List<Js> jsList = jsService.findByUserTypeAndDefault(request.getUserType(), true);
        if (CollectionUtils.isEmpty(jsList)) {
            throw new WebException(ErrorCodeEnum.REGISTER_DEFAULT_ROLE_NOT_FIND_ERROR);
        }

        Js js = jsList.get(0);
        if (js == null) {
            throw new WebException(ErrorCodeEnum.REGISTER_DEFAULT_ROLE_NOT_FIND_ERROR);
        }

        RegisterYhVO result = registerService.register(request, js);

        return ResponseUtil.success(result);
    }

    /**
     * 校验注册参数合法
     */
    private void checkRegisterRequest(RegisterRequest request) {

        if (request == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(request.getUserName())) {
            throw new WebException(ErrorCodeEnum.REGISTER_USERNAME_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(request.getUserType())) {
            throw new WebException(ErrorCodeEnum.REGISTER_SYSTEM_TYPE_PARAMS_ILLEGAL_ERROR);
        }
    }
}