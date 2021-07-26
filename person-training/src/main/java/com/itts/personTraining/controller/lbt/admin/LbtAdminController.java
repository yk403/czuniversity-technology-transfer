package com.itts.personTraining.controller.lbt.admin;


import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.lbt.Lbt;
import com.itts.personTraining.service.lbt.LbtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
@RestController
@RequestMapping(ADMIN_BASE_URL +"/v1/lbt")
@Api(value = "LbtAdminController", tags = "轮播图")
public class LbtAdminController {

    @Autowired
    private LbtService lbtService;

    /**
     * 查询
     */
    @GetMapping("/list/")
    @ApiOperation(value = "查询")
    public ResponseUtil getList(@RequestParam(value = "jgId") Long jgId) {
        return ResponseUtil.success(lbtService.findList(jgId));
    }
    /**
     * 新增
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Lbt lbt) throws WebException{
        checkPequest(lbt);
        return ResponseUtil.success(lbtService.add(lbt));
    }
    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Lbt lbt) throws WebException{
        checkPequest(lbt);
        Long id = lbt.getId();
        if(id == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Lbt lbt1 = lbtService.get(id);
        if(lbt1 == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(lbtService.update(lbt));
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
        Lbt lbt = lbtService.get(id);
        if(lbt == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(lbt);
    }
    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException{
        return ResponseUtil.success(lbtService.delete(id));
    }
    /**
     * 校验参数是否合法
     */
    private void checkPequest(Lbt lbt) throws WebException{
        if (lbt == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
        if (lbt.getTpmc() == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
        if (lbt.getJgId() == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
    }
}

