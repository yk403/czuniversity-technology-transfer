package com.itts.personTraining.controller.sj.user;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.service.ks.KsService;
import com.itts.personTraining.service.sj.SjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * @Author: Austin
 * @Data: 2021/6/8
 * @Version: 1.0.0
 * @Description: 实践门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/sj")
@Api(value = "sjController", tags = "实践-门户")
public class SjController {
    @Autowired
    private SjService sjService;

    /**
     * 根据用户id查询实践信息(实践通知)
     *
     * @return
     */
    @GetMapping("/findByYhId")
    @ApiOperation(value = "根据用户id查询实践信息(实践通知)")
    public ResponseUtil findByYhId() {
        return ResponseUtil.success(sjService.findByYhId());
    }

    /**
     * 根据创建人查询实践信息(前)
     *
     * @return
     */
    @GetMapping("/findByCjr")
    @ApiOperation(value = "根据创建人查询实践信息(前)")
    public ResponseUtil findByCjr() {
        return ResponseUtil.success(sjService.findByCjr());
    }

    /**
     * 新增实践(前)
     *
     * @param sjDTO
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增实践")
    public ResponseUtil userAdd(@RequestBody SjDTO sjDTO) throws WebException {
        //检查参数是否合法
        checkRequest(sjDTO);
        if (!sjService.userAdd(sjDTO)) {
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
    public ResponseUtil userUpdate(@RequestBody SjDTO sjDTO) throws WebException {
        Long id = sjDTO.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (sjService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!sjService.userUpdate(sjDTO)) {
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
     * 实践批量提交
     *
     * @param ids
     * @return
     */
    @PutMapping("/update/issueBatch")
    @ApiOperation(value = "实践批量提交")
    public ResponseUtil issueBatch(@RequestBody List<Long> ids) {
        if (!sjService.issueBatch(ids)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("实践提交成功!");
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
