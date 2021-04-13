package com.itts.userservice.controller.cz;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.dto.CzDTO;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.service.cz.CzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 操作表 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
@Api(tags = "操作管理")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/cz")
public class CzController {

    @Resource
    private CzService czService;

    /**
     * 查询当前菜单的操作
     *
     * @param id
     * @param cdid
     * @return
     */
    @GetMapping("/czlist/{id}/{cdid}")
    @ApiOperation(value = "获取操作列表")
    public ResponseUtil findcz(@PathVariable("id") Long id, @PathVariable("cdid") Long cdid) {
        List<CzDTO> cz = czService.findCz(id, cdid);
        return ResponseUtil.success(cz);
    }

    /**
     * 获取列表
     *
     * @param pageNum pageSize
     * @author fl
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil find(@ApiParam("当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam("每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam("操作名称") @RequestParam(value = "name", required = false) String name,
                             @ApiParam("系统类型") @RequestParam(value = "systemType", required = false) String systemType,
                             @ApiParam("模块类型") @RequestParam(value = "modelType", required = false) String modelType) {

        PageInfo<Cz> byPage = czService.findByPage(pageNum, pageSize, name, systemType, modelType);
        return ResponseUtil.success(byPage);
    }

    /**
     * 获取详情
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {

        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        Cz cz = czService.get(id);
        if (cz.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(cz);
    }

    /**
     * 新增
     *
     * @author FULI
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Cz cz) throws WebException {

        //检查参数是否合法
        checkRequest(cz);

        Cz add = czService.add(cz);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     *
     * @author FULI
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Cz cz) throws WebException {

        checkRequest(cz);

        if (cz.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        //检查数据库中是否存在要更新的数据
        Cz old = czService.get(cz.getId());
        if (old == null) {
            throw new WebException((ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR));
        }

        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(cz, old, "id", "cjsj", "cjr");

        czService.update(old);
        return ResponseUtil.success(old);
    }

    /**
     * 删除
     *
     * @author FULI
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {

        //检查参数是否为空
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        Cz cz = czService.get(id);
        if (cz == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        //设置删除状态
        cz.setSfsc(true);

        czService.update(cz);
        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkRequest(Cz cz) throws WebException {

        //如果参数为空，抛出异常
        if (cz == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cz.getCzmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cz.getCzbm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cz.getXtlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cz.getMklx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

