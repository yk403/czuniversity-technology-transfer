package com.itts.personTraining.controller.xyKc.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.xyKc.XyKc;
import com.itts.personTraining.service.xyKc.XyKcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.COURSE_EXISTS_ERROR;

/**
 * <p>
 * 学院课程关系表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-23
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/xyKc")
@Api(value = "XyKcAdminController", tags = "学院课程关系后台管理")
public class XyKcAdminController {

    @Autowired
    private XyKcService xyKcService;

    /**
     * 新增学院课程关系
     *
     * @param xyKc
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增学院课程关系")
    public ResponseUtil add(@RequestBody XyKc xyKc) throws WebException {
        if (!xyKcService.add(xyKc)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增学院课程关系!");
    }

    /**
     * 更新学院课程关系
     *
     * @param kc
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新学院课程关系")
    public ResponseUtil update(@RequestBody Kc kc) throws WebException {
        /*Long id = kc.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (kcService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!kcService.update(kc)) {
            throw new WebException(UPDATE_FAIL);
        }*/
        return ResponseUtil.success("更新学院课程关系成功!");

    }

    /**
     * 删除学院课程关系
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除学院课程关系")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        /*if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Kc kc = kcService.get(id);
        if (kc == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!kcService.delete(kc)) {
            throw new WebException(DELETE_FAIL);
        }*/
        return ResponseUtil.success("删除学院课程关系成功!");
    }

}

