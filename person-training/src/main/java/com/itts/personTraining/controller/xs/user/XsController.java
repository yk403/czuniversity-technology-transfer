package com.itts.personTraining.controller.xs.user;

import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.service.xs.XsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.*;
import static com.itts.common.enums.ErrorCodeEnum.*;

@RestController
@RequestMapping(BASE_URL + "/v1/xs")
@Api(value = "XsController", tags = "学生前台")

public class XsController {
    @Autowired
    private XsService xsService;

    /**
     * 查询学员综合信息
     *
     * @return
     */
    @GetMapping("/getByYhId/")
    @ApiOperation(value = "查询学员综合信息")
    public ResponseUtil getByYhId() {
        return ResponseUtil.success(xsService.getByYhId());
    }

    /**
     * 更新学生
     * @param stuDTO
     * @return
     * @throws WebException
     */
    @PutMapping("/update/")
    @ApiOperation(value = "更新学生")
    public ResponseUtil update(@RequestBody StuDTO stuDTO) throws WebException{
        checkRequest(stuDTO);
        Long id = stuDTO.getId();
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        StuDTO old = xsService.get(id);
        if(old==null){
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if(!xsService.update(stuDTO)){
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新学生成功!");
    }
    /**
     * 校验参数
     */
    private void checkRequest(StuDTO stuDTO) throws WebException {

        if (stuDTO == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(stuDTO.getXm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }


    }
}
