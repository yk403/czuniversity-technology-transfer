package com.itts.personTraining.controller.sj.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.service.kc.KcService;
import com.itts.personTraining.service.sj.SjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 实践表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-05-28
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/sj")
@Api(value = "SjAdminController", tags = "实践后台管理")
public class SjAdminController {
    @Autowired
    private SjService sjService;
    @Autowired
    private KcService kcService;

    /**
     * 查询实践列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取实践列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "pcId", required = false) Long pcId,
                                   @RequestParam(value = "sjlx", required = false) String sjlx,
                                   @RequestParam(value = "name", required = false) String name) {
        return ResponseUtil.success(sjService.findByPage(pageNum, pageSize, pcId, sjlx, name));
    }

    /**
     * 根据id查询实践详情
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id查询实践详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(sjService.get(id));
    }

    /**
     * 查询所有实践
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "查询所有实践")
    public ResponseUtil getAll() {
        return ResponseUtil.success(sjService.getAll());
    }

    /**
     * 新增实践
     *
     * @param sjDTO
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增实践")
    public ResponseUtil add(@RequestBody SjDTO sjDTO) throws WebException {
        //检查参数是否合法
        checkRequest(sjDTO);
        if (!sjService.add(sjDTO)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增实践成功!");
    }

    /**
     * 更新实践
     *
     * @param sjDTO
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新实践")
    public ResponseUtil update(@RequestBody SjDTO sjDTO) throws WebException {
        Long id = sjDTO.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (sjService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!sjService.update(sjDTO)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新实践成功!");

    }

    /**
     * 删除实践
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除实践")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        SjDTO sjDTO = sjService.get(id);
        if (sjDTO == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!sjService.delete(sjDTO)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除实践成功!");
    }

    /**
     * 实践批量下发
     *
     * @param ids
     * @return
     */
    @PutMapping("/update/issueBatch")
    @ApiOperation(value = "实践批量下发")
    public ResponseUtil issueBatch(@RequestBody List<Long> ids) {
        if (!sjService.issueBatch(ids)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("实践统一下发成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(SjDTO sjDTO) throws WebException {
        if (sjDTO == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        List<SjDTO> sjDTOs = sjService.getAll();
        for (SjDTO dto : sjDTOs) {
            if (dto.getXh().equals(sjDTO.getXh())) {
                throw new WebException(STUDENT_NUMBER_EXISTS_ERROR);
            }
        }
    }

}

