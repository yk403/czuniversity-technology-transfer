package com.itts.personTraining.controller.pyjh.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.PyJhDTO;
import com.itts.personTraining.model.pyjh.PyJh;
import com.itts.personTraining.service.pyjh.PyJhService;
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
 * 培养计划表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/pyJh")
@Api(value = "PyJhAdminController", tags = "培养计划后台管理")
public class PyJhAdminController {

    @Autowired
    private PyJhService pyJhService;

    /**
     * 查询培养计划列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取培养计划列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "pch", required = false) String pch,
                                   @RequestParam(value = "jylx", required = false) String jylx,
                                   @RequestParam(value = "jhmc", required = false) String jhmc,
                                   @RequestParam(value = "fjjgId", required = false) Long fjjgId) {
        return ResponseUtil.success(pyJhService.findByPage(pageNum, pageSize, pch, jylx, jhmc, fjjgId));
    }

    /**
     * 根据id查询培养计划
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取培养计划详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(pyJhService.get(id));
    }

    /**
     * 新增培养计划
     *
     * @param pyJhDTO
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增培养计划")
    public ResponseUtil add(@RequestBody PyJhDTO pyJhDTO) throws WebException {
        //检查参数是否合法
        checkRequest(pyJhDTO);
        if (!pyJhService.add(pyJhDTO)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增培养计划成功!");
    }

    /**
     * 更新培养计划
     *
     * @param pyJhDTO
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新培养计划")
    public ResponseUtil update(@RequestBody PyJhDTO pyJhDTO) throws WebException {
        Long id = pyJhDTO.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (pyJhService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!pyJhService.update(pyJhDTO)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新培养计划成功!");

    }

    /**
     * 删除培养计划
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除培养计划")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        PyJhDTO pyJhDTO = pyJhService.get(id);
        if (pyJhDTO == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!pyJhService.delete(pyJhDTO)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除培养计划成功!");
    }

    /**
     * 培养计划批量下发
     * @param ids
     * @return
     */
    @PutMapping("/update/issueBatch")
    @ApiOperation(value = "培养计划批量下发")
    public ResponseUtil issueBatch(@RequestBody List<Long> ids) {
        if (!pyJhService.issueBatch(ids)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("培养计划统一下发成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(PyJhDTO pyJhDTO) throws WebException {
        if (pyJhDTO == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (pyJhDTO.getPcId() == null) {
            throw new WebException(BATCH_NUMBER_ISEMPTY_ERROR);
        }
    }
}

