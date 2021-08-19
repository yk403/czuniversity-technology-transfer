package com.itts.personTraining.controller.zj.user;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.ZjInfoDTO;
import com.itts.personTraining.model.zj.Zj;
import com.itts.personTraining.service.zj.ZjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.PROFESSOR_NUMBER_EXISTS_ERROR;
import static com.itts.personTraining.enums.UserTypeEnum.OUT_PROFESSOR;
import static com.itts.personTraining.enums.UserTypeEnum.PROFESSOR;

/**
 * @Author: Austin
 * @Data: 2021/8/10
 * @Version: 1.0.0
 * @Description: 专家门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/zj")
@Api(value = "ZjController", tags = "专家-门户")
public class ZjController {

    @Autowired
    private ZjService zjService;

    /**
     * 查询专家综合信息
     *
     * @return
     */
    @GetMapping("/getByYhId")
    @ApiOperation(value = "查询专家综合信息")
    public ResponseUtil getByYhId() {
        return ResponseUtil.success(zjService.getByYhId());
    }
    /**
     * 更新专家信息
     * @param zj
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新专家信息")
    public ResponseUtil update(@RequestBody ZjInfoDTO zjInfoDTO, HttpServletRequest request) throws WebException {
        Zj zj=new Zj();
        BeanUtils.copyProperties(zjInfoDTO,zj);
        //检查数据库中是否存在要更新的数据
        Zj zjOld = zjService.get(zj.getId());
        if (zjOld == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        checkUpdateRequest(zj);
        if (!zjService.update(zj, request.getHeader("token"))) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success(zjInfoDTO);

    }
    /**
     * 更新校验参数
     */
    private void checkUpdateRequest(Zj zj) throws WebException {
        Zj zjOld = zjService.get(zj.getId());
        if (zjOld == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!zjOld.getDh().equals(zj.getDh())) {
            List<Zj> zjList = zjService.getAll();
            for (Zj zj1 : zjList) {
                if (zj1.getDh().equals(zj.getDh())) {
                    throw new WebException(PROFESSOR_PHONE_EXISTS_ERROR);
                }
                if (zj1.getBh().equals(zj.getBh())) {
                    throw new WebException(PROFESSOR_NUMBER_EXISTS_ERROR);
                }
            }
        }
    }
}
