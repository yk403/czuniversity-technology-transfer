package com.itts.userservice.controller.cd;


import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.service.cd.CdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

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

        PageInfo<Cd> byPage = cdService.findByPage(pageNum, pageSize, name, systemType, modelType);
        return ResponseUtil.success(byPage);
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

        Cd cd = cdService.get(id);
        return ResponseUtil.success(cd);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody Cd cd) throws WebException {

        checkRequst(cd);

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            cd.setCjr(loginUser.getUserId());
            cd.setGxr(loginUser.getUserId());
        }

        Date now = new Date();
        cd.setCjsj(now);
        cd.setGxsj(now);

        //设置层级
        if (cd.getFjcdId() == 0L) {
            cd.setCj(cd.getCdbm());
        } else {

            Cd fjcd = cdService.get(cd.getFjcdId());
            cd.setCj(fjcd.getCj() + "-" + cd.getCdbm());
        }

        Cd add = cdService.add(cd);

        return ResponseUtil.success(add);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Cd cd) {

        checkRequst(cd);

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

        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(cd, old, "id", "chsj", "cjr");

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            old.setGxr(loginUser.getUserId());
        }
        old.setGxsj(new Date());

        //设置层级
        if (cd.getFjcdId() == 0L) {
            old.setCj(cd.getCdbm());
        } else {

            Cd fjcd = cdService.get(cd.getFjcdId());
            old.setCj(fjcd.getCj() + "-" + cd.getCdbm());
        }

        cdService.update(old);
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
        //设置删除状态，更新删除时间
        cd.setSfsc(true);
        cd.setCjsj(new Date());

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            cd.setGxr(loginUser.getUserId());
        }

        cdService.update(cd);
        return ResponseUtil.success();
    }

    /**
     * 校验参数
     */
    private void checkRequst(Cd cd) throws WebException {

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

