package com.itts.personTraining.controller.lmgl.admin;


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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
@RestController
@RequestMapping(ADMIN_BASE_URL +"/v1/lmgl")
@Api(tags = "栏目管理")
public class LmglAdminController {

    @Autowired
    private LmglService lmglService;

    /**
     * 查询
     */
    @GetMapping("/list/")
    @ApiOperation(value = "查询")
    public ResponseUtil getList(@RequestParam(value = "jgId") Long jgId) {
        return ResponseUtil.success(lmglService.findList(jgId));
    }
    /**
     * 新增
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Lmgl lmgl) throws WebException {
        checkPequest(lmgl);
        return ResponseUtil.success(lmglService.add(lmgl));
    }
    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Lmgl lmgl) throws WebException{
        checkPequest(lmgl);
        Long id = lmgl.getId();
        if(id == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Lmgl lmgl1 = lmglService.get(id);
        if(lmgl1 == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(lmglService.update(lmgl));
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
    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException{
        return ResponseUtil.success(lmglService.delete(id));
    }
    @ApiOperation(value = "上")
    @PutMapping("/up/")
    public ResponseUtil up(@RequestParam(value = "jgId")Long jgId,
                           @RequestParam(value = "id")Long id)throws WebException{
        return ResponseUtil.success(lmglService.up(jgId, id));
    }
    @ApiOperation(value = "下")
    @PutMapping("/down/")
    public ResponseUtil down(@RequestParam(value = "jgId")Long jgId,
                             @RequestParam(value = "id")Long id)throws WebException{
        return ResponseUtil.success(lmglService.down(jgId, id));
    }
    /**
     * 校验参数是否合法
     */
    private void checkPequest(Lmgl lmgl) throws WebException{
        if (lmgl == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
        if (lmgl.getMc() == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
        if (lmgl.getJgId() == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
    }
}

