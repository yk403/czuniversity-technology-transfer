package com.itts.personTraining.controller.xs.user;


import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.service.xs.XsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.*;
import static com.itts.common.enums.ErrorCodeEnum.*;

@RestController
@RequestMapping(BASE_URL + "/v1/xs")
@Api(value = "XsController", tags = "学生前台")

public class XsController {
    @Autowired
    private XsService xsService;

    /**
     * 查询学员详情
     *
     *
     * @return
     */
    @GetMapping("/get/")
    @ApiOperation(value = "获取学员详情")
    public ResponseUtil get() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return ResponseUtil.success(xsService.get(userId));
    }

    /**
     * 更新学生
     * @param xs
     * @return
     * @throws WebException
     */
    @PutMapping("/update/")
    @ApiOperation(value = "更新学生")
    public ResponseUtil update(@RequestBody Xs xs) throws WebException{
        checkRequest(xs);
        Long id = xs.getId();
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Xs old = xsService.get(id);
        if(old==null){
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if(!xsService.update(xs)){
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新学生成功!");
    }
    /**
     * 校验参数
     */
    private void checkRequest(Xs xs) throws WebException {

        if (xs == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(xs.getXm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }


    }
}
