package com.itts.userservice.controller.cz;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.ResponseUtil;
import com.itts.userservice.model.cz.TCz;
import com.itts.userservice.service.cz.TCzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

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
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/tCz")
public class TCzController {

    @Resource
    private TCzService tCzService;

    /**
     * 获取列表
     *
     * @param pageNum pageSize
     * @author fl
     * 54860a82e5df8ecfd44cc08cb8654e5638c4e77b
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil find(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageInfo<TCz> byPage = tCzService.findByPage(pageNum, pageSize);
        return ResponseUtil.success(byPage);
    }

    /**
     * 新增
     *
     * @author FULI
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody TCz tCz) throws WebException {
        //检查参数是否合法
        checkPequest(tCz);
        //检查数据库中是否存在要更新的数据
        TCz add = tCzService.add(tCz);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     *
     * @author FULI
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/{id}")
    public ResponseUtil update(@PathVariable("id") Long id, @RequestBody TCz tCz) throws WebException {
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        TCz tCz1 = tCzService.get(id);
        if (tCz1 == null) {
            throw new WebException((ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR));
        }
        checkPequest(tCz);
        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(tCz, tCz1, "id", "cjsj", "cjr");
        tCzService.update(tCz1);
        return ResponseUtil.success(tCz1);
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
        TCz tCz = tCzService.get(id);
        if (tCz == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        //设置删除状态，更新删除时间
        tCz.setSfsc(false);
        tCz.setGxsj(new Date());
        tCzService.update(tCz);
        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkPequest(TCz tCz) throws WebException {
        //如果参数为空，抛出异常
        if (tCz == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }


}

