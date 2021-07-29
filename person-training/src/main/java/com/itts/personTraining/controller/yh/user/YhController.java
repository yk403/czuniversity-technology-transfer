package com.itts.personTraining.controller.yh.user;

import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.userservice.RoleFeignService;
import com.itts.personTraining.feign.userservice.UserFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/23
 * @Version: 1.0.0
 * @Description: 用户前台
 */
@RestController
@RequestMapping(BASE_URL + "/v1/yh")
@Api(value = "YhController", tags = "用户-门户")
public class YhController {

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private RoleFeignService roleFeignService;

    /**
     * 获取当前登陆用户信息
     */
    @GetMapping("/get")
    public ResponseUtil get() {
        return userFeignService.get();
    }

    @ApiOperation(value = "获取用户所属系统")
    @GetMapping("/find/user/systems/")
    public ResponseUtil getUserSystems() {

        ResponseUtil result = userFeignService.findUserSystems();
        return result;
    }

    @ApiOperation(value = "获取用户角色信息")
    @GetMapping("/find/user/role/")
    public ResponseUtil getUserRole(){

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if(loginUser == null){
            throw new WebException(ErrorCodeEnum.NO_LOGIN_ERROR);
        }

        ResponseUtil result = roleFeignService.getByUserId(loginUser.getUserId());

        return result;
    }
}
