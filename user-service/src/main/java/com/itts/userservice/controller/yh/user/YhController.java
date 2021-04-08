package com.itts.userservice.controller.yh.user;

import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.service.yh.YhService;
import com.itts.userservice.vo.YhVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/29
 */
@Api(tags = "用户")
@RequestMapping(SystemConstant.BASE_URL + "/v1/yh")
@RestController
public class YhController {

    @Autowired
    private YhService yhService;

    /**
     * 获取用户信息
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/get/")
    @ApiOperation(value = "获取用户信息")
    public ResponseUtil get() {

        //获取登录信息
        LoginUser loginUser = SystemConstant.threadLocal.get();

        Yh yh = yhService.get(loginUser.getUserId());

        if (yh == null) {

            throw new WebException(ErrorCodeEnum.USER_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(yh);
    }

    /**
     * 获取用户菜单信息
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/menus/")
    @ApiOperation(value = "获取用户菜单信息")
    public ResponseUtil findMenus() {

        //获取登录用户信息
        LoginUser loginUser = SystemConstant.threadLocal.get();

        YhVO yhVO = yhService.findMenusByUserID(loginUser.getUserId(), loginUser.getSystemType());
        return ResponseUtil.success(yhVO);
    }
}