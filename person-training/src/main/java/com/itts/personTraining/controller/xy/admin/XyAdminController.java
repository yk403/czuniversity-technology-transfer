package com.itts.personTraining.controller.xy.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.service.xy.XyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;

/**
 * <p>
 * 学院表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-23
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/xy")
@Api(value = "XyAdminController", tags = "学院后台管理")
public class XyAdminController {
    
    @Autowired
    private XyService xyService;

    /**
     * 查询所有学院信息
     * @return
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有学院信息")
    public ResponseUtil getAll() {
        return ResponseUtil.success(xyService.getAll());
    }

    /**
     * 新增学院
     *
     * @param xy
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增学院")
    public ResponseUtil add(@RequestBody Xy xy) throws WebException {
        //检查参数是否合法
        checkRequest(xy);
        if (!xyService.add(xy)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增学院成功!");
    }

    /**
     * 更新学院
     *
     * @param xy
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新学院")
    public ResponseUtil update(@RequestBody Xy xy) throws WebException {
        Long id = xy.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (xyService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (xy.getXymc() != null) {
            checkRequest(xy);
        } else {
            throw new WebException(INSTITUTE_NAME_ISEMPTY_ERROR);
        }
        if (!xyService.update(xy)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新学院成功!");

    }

    /**
     * 删除学院
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除学院")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Xy xy = xyService.get(id);
        if (xy == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!xyService.delete(xy)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除学院成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(Xy xy) throws WebException {
        if (xy == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        List<Xy> xyList = xyService.getAll();
        for (Xy xy1 : xyList) {
            if (xy1.getXymc().equals(xy.getXymc())) {
                throw new WebException(INSTITUTE_NAME_EXISTS_ERROR);
            }
        }
    }

}

