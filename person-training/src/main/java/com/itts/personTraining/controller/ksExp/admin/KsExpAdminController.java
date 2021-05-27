package com.itts.personTraining.controller.ksExp.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.service.ksExp.KsExpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.UPDATE_FAIL;

/**
 * <p>
 * 考试扩展表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-05-13
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/ksExp")
@Api(value = "KsExpAdminController", tags = "考试扩展后台管理")
public class KsExpAdminController {

    @Autowired
    private KsExpService ksExpService;

    /**
     * 根据条件查询考试扩展信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @ApiOperation(value = "根据条件查询考试扩展信息")
    public ResponseUtil get(@RequestParam(value = "id", required = false) Long id,
                            @RequestParam(value = "ksId", required = false) Long ksId) {
        return ResponseUtil.success(ksExpService.get(id,ksId));
    }

    /**
     * 根据条件查询考试扩展信息(继续教育)
     *
     * @param id
     * @return
     */
    @GetMapping("/getByCondition")
    @ApiOperation(value = "根据条件查询考试扩展信息(继续教育)")
    public ResponseUtil getByCondition(@RequestParam(value = "id", required = false) Long id,
                            @RequestParam(value = "ksId", required = false) Long ksId) {
        return ResponseUtil.success(ksExpService.getByCondition(id,ksId));
    }


    /**
     * 更新考试扩展信息
     * @param ksExpDTO
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新考试扩展信息")
    public ResponseUtil update(@RequestBody KsExpDTO ksExpDTO) throws WebException {
        Long id = ksExpDTO.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        List<KsExpDTO> ksExpDTOS = ksExpService.get( id,null);
        if (ksExpDTOS == null || ksExpDTOS.size() == 0) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!ksExpService.update(ksExpDTO)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新考试扩展信息成功!");

    }

    /**
     * 根据考试扩展id删除考试扩展信息
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据考试扩展id删除考试扩展信息")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        List<KsExpDTO> ksExpDTOs = ksExpService.get(id,null);
        if (ksExpDTOs == null || ksExpDTOs.size() == 0) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!ksExpService.delete(ksExpDTOs.get(0))) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除考试扩展信息成功!");
    }
}

