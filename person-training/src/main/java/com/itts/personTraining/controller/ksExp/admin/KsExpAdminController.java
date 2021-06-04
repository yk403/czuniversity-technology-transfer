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
import static com.itts.personTraining.enums.EduTypeEnum.ACADEMIC_DEGREE_EDUCATION;
import static com.itts.personTraining.enums.EduTypeEnum.ADULT_EDUCATION;

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
     * @param ksExpDTOs
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新考试扩展信息")
    public ResponseUtil update(@RequestBody List<KsExpDTO> ksExpDTOs) throws WebException {
        String jylx = ksExpDTOs.get(0).getJylx();
        if (jylx == null) {
            throw new WebException(TEACH_TYPE_ISEMPTY_ERROR);
        }
        if (!ACADEMIC_DEGREE_EDUCATION.getKey().equals(jylx) && !ADULT_EDUCATION.getKey().equals((jylx))) {
            throw new WebException(EDU_TYPE_ERROR);
        }
        if (!ksExpService.update(ksExpDTOs,jylx)) {
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

