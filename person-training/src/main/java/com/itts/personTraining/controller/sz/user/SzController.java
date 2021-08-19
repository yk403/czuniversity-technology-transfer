package com.itts.personTraining.controller.sz.user;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.SzMsgDTO;
import com.itts.personTraining.enums.UserTypeEnum;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xs.XsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.UPDATE_FAIL;

/**
 * @Author: Austin
 * @Data: 2021/6/23
 * @Version: 1.0.0
 * @Description: 师资门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/sz")
@Api(value = "SzController", tags = "师资-门户")
public class SzController {
    @Autowired
    private SzService szService;

    /**
     * 查询师资综合信息
     *
     * @return
     */
    @GetMapping("/getByYhId")
    @ApiOperation(value = "查询师资综合信息")
    public ResponseUtil getByYhId() {
        return ResponseUtil.success(szService.getByYhId());
    }

    /**
     * 根据机构编号查询师资信息
     */
    @GetMapping("/getByJgBh")
    @ApiOperation(value = "根据机构编号查询师资信息")
    public ResponseUtil getByJgBh(@RequestParam("jgCode") String jgCode) {
        return ResponseUtil.success(szService.getByJgBh(jgCode));
    }
    /**
     * 更新师资
     *
     * @param
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新师资")
    public ResponseUtil update(@RequestBody SzMsgDTO szMsgDTO, HttpServletRequest request) throws WebException {
        Sz sz=new Sz();
        BeanUtils.copyProperties(szMsgDTO,sz);
        checkRequest(sz);
        Long id = sz.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        Sz szOld = szService.get(id);
        if (szOld == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!szOld.getDsbh().equals(sz.getDsbh())) {
            if (szService.selectByCondition(sz.getDsbh(),null,null,null) != null) {
                throw new WebException(TEACHER_NUMBER_EXISTS_ERROR);
            }
        }
        if (!szService.update(sz,request.getHeader("token"))) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success(szMsgDTO);

    }
    /**
     * 校验参数
     */
    private void checkRequest(Sz sz) throws WebException {
        if (sz == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        String dslb = sz.getDslb();
        if (dslb == null) {
            throw new WebException(TEACHER_TYPE_ISEMPTY_ERROR);
        }
        if (!dslb.equals(UserTypeEnum.TUTOR.getKey()) && !dslb.equals(UserTypeEnum.CORPORATE_MENTOR.getKey()) && !dslb.equals(UserTypeEnum.TEACHER.getKey()) && !dslb.equals(UserTypeEnum.SCHOOL_LEADER.getKey())) {
            throw new WebException(TEACHER_TYPE_ERROR);
        }
       /* if (sz.getSsjgId() == null) {
            throw new WebException(ORGANIZATION_ISEMPTY_ERROR);
        }*/
    }
}
