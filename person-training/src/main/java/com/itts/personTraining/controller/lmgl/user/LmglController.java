package com.itts.personTraining.controller.lmgl.user;


import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.lmgl.Lmgl;
import com.itts.personTraining.service.lmgl.LmglService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
@RestController
@RequestMapping(BASE_URL +"/v1/lmgl")
@Api(tags = "栏目门户")
public class LmglController {

    @Autowired
    private LmglService lmglService;

    /**
     * 查询
     */
    @GetMapping("/list/")
    @ApiOperation(value = "查询")
    public ResponseUtil getList(@RequestParam(value = "jgId") Long jgId) {
        if(jgId == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        return ResponseUtil.success(lmglService.findList(jgId));
    }

    /**
     * 查询
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "查询")
    public ResponseUtil get(@PathVariable("id") Long id) throws WebException{
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Lmgl lmgl = lmglService.get(id);
        if(lmgl == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(lmgl);
    }

}

