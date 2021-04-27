package com.itts.personTraining.controller.zy.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.model.zy.Zy;
import com.itts.personTraining.service.zy.ZyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.INSTITUTE_NAME_EXISTS_ERROR;

/**
 * <p>
 * 专业表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-26
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/zy")
@Api(value = "ZyAdminController", tags = "专业后台管理")
public class ZyAdminController {
    @Autowired
    private ZyService zyService;

    /**
     * 根据学院id查询专业信息
     * @return
     */
    @GetMapping("/getByXyId/{xyId}")
    @ApiOperation(value = "根据学院id查询专业信息")
    public ResponseUtil getByXyId(@PathVariable("xyId") Long xyId) {
        return ResponseUtil.success(zyService.getByXyId(xyId));
    }

    /**
     * 根据id查询专业信息
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据专业id查询专业信息")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(zyService.get(id));
    }

    /**
     * 查询所有专业信息
     * @return
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "查询所有专业信息")
    public ResponseUtil getAll() {
        return ResponseUtil.success(zyService.getAll());
    }

    /**
     * 新增专业
     *
     * @param zy
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增专业")
    public ResponseUtil add(@RequestBody Zy zy) throws WebException {
        //检查参数是否合法
        checkRequest(zy);
        if (!zyService.add(zy)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增专业成功!");
    }

    /**
     * 更新专业
     *
     * @param zy
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新专业")
    public ResponseUtil update(@RequestBody Zy zy) throws WebException {
        Long id = zy.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (zyService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (zy.getMc() != null) {
            checkRequest(zy);
        } else {
            throw new WebException(INSTITUTE_NAME_ISEMPTY_ERROR);
        }
        if (!zyService.update(zy)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新专业成功!");

    }

    /**
     * 删除专业
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除专业")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Zy zy = zyService.get(id);
        if (zy == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!zyService.delete(zy)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除专业成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(Zy zy) throws WebException {
        if (zy == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (zy.getMc() == null) {
            throw new WebException(NAME_ISEMPTY_ERROR);
        }
        if (zy.getXyId() == null) {
            throw new WebException(INSTITUTE_ID_ISEMPTY_ERROR);
        }
        List<Zy> zyList = zyService.getAll();
        for (Zy zy1 : zyList) {
            if (zy1.getMc().equals(zy.getMc())) {
                throw new WebException(DOMAIN_NAME_ISEMPTY_ERROR);
            }
        }
    }
}

