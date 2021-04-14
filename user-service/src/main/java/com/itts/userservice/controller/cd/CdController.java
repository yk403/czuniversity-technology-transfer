package com.itts.userservice.controller.cd;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.dto.GetCdAndCzDTO;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.request.AddCdRequest;
import com.itts.userservice.service.cd.CdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
@Api(tags = "菜单控制")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/cd")
public class CdController {

    @Resource
    private CdService cdService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list/")
    public ResponseUtil find(@ApiParam("当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam("每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam("菜单名称") @RequestParam(value = "name", required = false) String name,
                             @ApiParam("系统类型") @RequestParam(value = "systemType", required = false) String systemType,
                             @ApiParam("模块类型") @RequestParam(value = "modelType", required = false) String modelType) {

        PageInfo<GetCdAndCzDTO> pageInfo = cdService.findByPage(pageNum, pageSize, name, systemType, modelType);
        return ResponseUtil.success(pageInfo);
    }
    /**
     * 通过名称和编码获取列表
     */
    @ApiOperation(value = "通过名称和编码获取列表")
    @GetMapping("/getlistBynameorcode/")
    public ResponseUtil findBynameorcode(@ApiParam("当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam("每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam("菜单名称或菜单编码") @RequestParam(value = "qurey") String parameter,
                             @ApiParam("系统类型") @RequestParam(value = "systemType", required = false) String systemType,
                             @ApiParam("模块类型") @RequestParam(value = "modelType", required = false) String modelType) {
        if(parameter==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        PageInfo<GetCdAndCzDTO> pageInfo = cdService.findByNameorCodePage(pageNum, pageSize, parameter, systemType, modelType);
        return ResponseUtil.success(pageInfo);
    }

    /**
     * 获取详情
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {

        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        GetCdAndCzDTO cd = cdService.get(id);
        if (cd == null || cd.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(cd);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody AddCdRequest cd) throws WebException {

        checkRequest(cd);
        Cd add = cdService.add(cd);

        return ResponseUtil.success(add);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody AddCdRequest cd) {

        checkRequest(cd);

        Long id = cd.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        //检查数据库中是否存在要更新的数据
        Cd old = cdService.get(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        cdService.update(cd, old);
        return ResponseUtil.success(old);
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

        Cd cd = cdService.get(id);
        if (cd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        //判断当前菜单下是否有子级
        Long count = cdService.countByParentId(id);
        if(count > 0){
            throw new WebException(ErrorCodeEnum.USER_DELETE_MENU_HAVE_CHILD_ERROR);
        }

        cdService.delete(cd);
        return ResponseUtil.success();
    }

    /**
     * 校验参数
     */
    private void checkRequest(Cd cd) throws WebException {

        if (cd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cd.getCdmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cd.getCdbm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (cd.getFjcdId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cd.getXtlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cd.getMklx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

