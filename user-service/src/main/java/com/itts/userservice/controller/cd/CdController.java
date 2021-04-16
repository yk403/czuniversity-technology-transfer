package com.itts.userservice.controller.cd;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.service.cd.CdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "菜单控制")
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
    public ResponseUtil find(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageInfo<Cd> byPage = cdService.findByPage(pageNum, pageSize);
        return ResponseUtil.success(byPage);
    }

    /**
     * 获取详情
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
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

        Cd add = cdService.add(cd);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/{id}")
    public ResponseUtil update(@PathVariable("id") Long id, @RequestBody Cd cd) {
        //检查参数是否合法
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        Cd cd1 = cdService.get(id);
        if (cd1 == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        checkRequst(cd);
        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(cd, cd1, "id", "chsj", "cjr");
        cdService.update(cd1);
        return ResponseUtil.success(cd1);
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
    }
}

