package com.itts.userservice.controller.yh.user;

import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.request.yh.UpdateUserRequest;
import com.itts.userservice.service.js.JsService;
import com.itts.userservice.service.yh.YhJsGlService;
import com.itts.userservice.service.yh.YhService;
import com.itts.userservice.vo.GetJsVO;
import com.itts.userservice.vo.yh.GetYhVO;
import com.itts.userservice.vo.yh.YhVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;

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

    @Autowired
    private YhJsGlService yhJsGlService;

    @Autowired
    private JsService jsService;

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
        LoginUser loginUser = threadLocal.get();

        Yh yh = yhService.get(loginUser.getUserId());

        if (yh == null) {

            throw new WebException(ErrorCodeEnum.USER_NOT_FIND_ERROR);
        }

        GetYhVO getYhVO = new GetYhVO();
        BeanUtils.copyProperties(yh, getYhVO);

        return ResponseUtil.success(getYhVO);
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
        LoginUser loginUser = threadLocal.get();

        YhVO yhVO = yhService.findMenusByUserID(loginUser.getUserId(), loginUser.getSystemType());
        return ResponseUtil.success(yhVO);
    }

    /**
     * 获取用户菜单操作信息
     */
    @GetMapping("/menus/operation/")
    @ApiOperation(value = "获取用户菜单操作信息")
    public ResponseUtil findMenusAndOperation() {
        Long id = threadLocal.get().getUserId();
        List<Long> longs = yhJsGlService.fingByYhid(id);

        GetJsVO jsCdCzGl = null;
        for (Long jsId : longs) {
            jsCdCzGl = jsService.getJsCdCzGl(jsId);
        }
        return ResponseUtil.success(jsCdCzGl);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/update/")
    @ApiOperation(value = "更新用户信息")
    public ResponseUtil update(@RequestBody UpdateUserRequest updateUserRequest) {

        if (updateUserRequest == null) {

            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateUserRequest.getYhtx())
                && StringUtils.isBlank(updateUserRequest.getLxdh())
                && StringUtils.isBlank(updateUserRequest.getYhyx())) {

            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        LoginUser loginUser = threadLocal.get();

        if (loginUser == null) {

            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Yh yh = yhService.get(loginUser.getUserId());
        if (yh == null) {

            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        //设置用户联系电话
        if (StringUtils.isNotBlank(updateUserRequest.getLxdh())) {
            yh.setLxdh(updateUserRequest.getLxdh());
        }

        //设置用户邮箱
        if (StringUtils.isNotBlank(updateUserRequest.getYhyx())) {
            yh.setYhyx(updateUserRequest.getYhyx());
        }

        //设置用户头像
        if (StringUtils.isNotBlank(updateUserRequest.getYhtx())) {
            yh.setYhtx(updateUserRequest.getYhtx());
        }

        yh.setGxsj(new Date());

        yhService.updateById(yh);

        return ResponseUtil.success(yh);
    }
}