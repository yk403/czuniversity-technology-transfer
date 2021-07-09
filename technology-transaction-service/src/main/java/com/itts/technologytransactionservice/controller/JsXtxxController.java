package com.itts.technologytransactionservice.controller;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.JsXtxx;
import com.itts.technologytransactionservice.model.LyBm;
import com.itts.technologytransactionservice.service.JsXtxxService;
import com.itts.technologytransactionservice.service.LyBmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/*
 *@Auther: yukai
 *@Date: 2021/07/06/10:00
 *@Desription:
 */
@RequestMapping(BASE_URL + "/v1/JsXtxx")
@Api(value = "JsXtxxController", tags = "系统消息管理")
@RestController
public class JsXtxxController {
    @Autowired
    private JsXtxxService jsXtxxService;
    /**
     * 获取列表
     */
    @ApiOperation(value = "获取列表")
    @PostMapping("/list")
    public ResponseUtil find(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(jsXtxxService.findJsXtxx(params));
    }
    /**
     * 更新课程
     *
     * @param lyBm
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新系统消息")
    public ResponseUtil update(@RequestBody JsXtxx jsXtxx) throws WebException {
        Long id = jsXtxx.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (jsXtxxService.getById(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新数据
        if (!jsXtxxService.updateById(jsXtxx)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新系统消息成功!");

    }
}
