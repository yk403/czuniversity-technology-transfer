package com.itts.userservice.controller.shzd;


import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.shzd.Shzd;
import com.itts.userservice.service.shzd.ShzdService;
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
@Slf4j
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/shzd")
public class ShzdController {

    @Resource
    private ShzdService shzdService;

    /**
     * 通过名称或编码查询
     */
    @GetMapping("/queryDictionary/{string}")
    @ApiOperation(value = "通过名称或编码查询")
    public ResponseUtil getByNameAndCode(@PathVariable("string") String string) throws WebException {
        Shzd shzd = shzdService.selectByString(string);
        return ResponseUtil.success(shzd);
    }

    /**
     * 新增
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Shzd shzd) throws WebException {
        checkPequest(shzd);
        Shzd add = shzdService.add(shzd);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/{id}")
    public ResponseUtil update(@PathVariable("id") Long id, @RequestBody Shzd shzd) throws WebException {
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Shzd shzd1 = shzdService.get(id);
        if (shzd1 == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        checkPequest(shzd);
        BeanUtils.copyProperties(shzd, shzd1, "id", "cjsj", "cjr");
        shzdService.update(shzd1);
        return ResponseUtil.success(shzd1);
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
        Shzd shzd = shzdService.get(id);
        if (shzd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        shzd.setSfsc(false);
        shzd.setGxsj(new Date());
        shzdService.update(shzd);
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
            Shzd shzd = shzdService.get(ids.get(i));
            if (shzd == null) {
                log.error("【数据字典-批量删除】数据字典不存在");
            }
            shzd.setSfsc(false);
            shzd.setGxsj(new Date());
            shzdService.update(shzd);
        }
        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkPequest(Shzd shzd) throws WebException {
        //如果参数为空，抛出异常
        if (shzd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

