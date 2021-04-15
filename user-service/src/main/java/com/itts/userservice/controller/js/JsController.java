package com.itts.userservice.controller.js;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.request.AddJsRequest;
import com.itts.userservice.service.js.JsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/js")
public class JsController {

    @Resource
    private JsService jsService;

    /**
     * 获取列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list/")
    public ResponseUtil find(@ApiParam("当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam("当前页数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam("角色名称") @RequestParam(value = "name", required = false) String name,
                             @ApiParam("系统类型") @RequestParam(value = "systemType", required = false) String systemType) {

        PageInfo<Js> byPage = jsService.findByPage(pageNum, pageSize, name, systemType);
        return ResponseUtil.success(byPage);
    }

    /**
     * 获取详情
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        Js Js = jsService.get(id);
        return ResponseUtil.success(Js);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody AddJsRequest Js) throws WebException {
        checkRequst(Js);
        Js add = jsService.add(Js);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Js Js) {
        Long id = Js.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        Js Js1 = jsService.get(id);
        if (Js1 == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        checkRequst(Js);
        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(Js, Js1, "id", "chsj", "cjr");
        jsService.update(Js1);
        return ResponseUtil.success(Js1);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Js Js = jsService.get(id);
        if (Js == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        //设置删除状态，更新删除时间
        Js.setSfsc(true);
        Js.setCjsj(new Date());
        jsService.update(Js);
        return ResponseUtil.success();
    }

    /**
     * 校验参数
     */
    private void checkRequst(Js js) throws WebException {

        if (js == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(js.getJsmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(js.getJsbm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(js.getXtlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

