package com.itts.userservice.controller.sjzd;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.service.sjzd.SjzdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
@Api(tags = "数字字典")
@Slf4j
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/sjzd")
public class SjzdController {

    @Resource
    private SjzdService sjzdService;

    /**
     * 通过名称或编码查询
     */
    @GetMapping("/queryDictionary/")
    @ApiOperation(value = "通过名称或编码查询")
    public ResponseUtil getByNameAndCode(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         @RequestParam("string") String string,@RequestParam String ssmk) throws WebException {
        Sjzd sjzd = sjzdService.selectByString(pageNum,pageSize,string,ssmk);
        return ResponseUtil.success(sjzd);
    }

    /**
     * 获取列表
     */
    @GetMapping("/getlist/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageInfo<Sjzd> byPage = sjzdService.findByPage(pageNum, pageSize);
        return ResponseUtil.success(byPage);
    }
    /**
     * 获取指定模块列表
     */
    @GetMapping("/getappointlist/")
    @ApiOperation(value = "获取指定模块列表")
    public ResponseUtil getAppointList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "ssmk") String ssmk) {
        PageInfo<Sjzd> byPage = sjzdService.findAppointByPage(pageNum, pageSize,ssmk);
        return ResponseUtil.success(byPage);
    }
    /**
     * 新增
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Sjzd sjzd) throws WebException {
        checkPequest(sjzd);
        Sjzd add = sjzdService.add(sjzd);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Sjzd sjzd) throws WebException {
        Long id = sjzd.getId();
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Sjzd sjzd1 = sjzdService.get(id);
        if (sjzd1 == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        checkPequest(sjzd);
        BeanUtils.copyProperties(sjzd, sjzd1, "id", "cjsj", "cjr");
        sjzdService.update(sjzd1);
        return ResponseUtil.success(sjzd1);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Sjzd sjzd = sjzdService.get(id);
        if (sjzd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        sjzd.setSfsc(true);
        sjzd.setGxsj(new Date());
        sjzdService.update(sjzd);
        return ResponseUtil.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/deleteBatch")
    public ResponseUtil deleteBatch(@RequestBody List<Long> ids) {
        if (ids == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        for (int i = 0; i < ids.size(); i++) {
            Sjzd sjzd = sjzdService.get(ids.get(i));
            if (sjzd == null) {
                log.error("【数据字典-批量删除】数据字典不存在");
            }
            sjzd.setSfsc(true);
            sjzd.setGxsj(new Date());
            sjzdService.update(sjzd);
        }
        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkPequest(Sjzd sjzd) throws WebException {
        //如果参数为空，抛出异常
        if (sjzd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}
