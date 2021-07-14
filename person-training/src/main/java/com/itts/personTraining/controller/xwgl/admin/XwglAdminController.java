package com.itts.personTraining.controller.xwgl.admin;

import com.github.pagehelper.PageInfo;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xwgl.Xwgl;
import com.itts.personTraining.service.xwgl.XwglService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-09
 */
@RestController
@RequestMapping(ADMIN_BASE_URL +"/v1/xwgl")
@Api(value = "XwglAdminController", tags = "新闻后台管理")
public class XwglAdminController {

    @Resource
    private XwglService xwglService;

    /**
     * 查询
     */
    @GetMapping("/list/")
    @ApiOperation(value = "查询新闻")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "jgId",required = false) Long jgId,
                                         @RequestParam(value = "zt",required = false) String zt,
                                         @RequestParam(value = "lx",required = false) String lx) throws WebException {
        return ResponseUtil.success(xwglService.findByPage(pageNum, pageSize, jgId, zt, lx));
    }
    /**
     * 新增
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Xwgl xwgl) throws WebException{
        checkPequest(xwgl);
        return ResponseUtil.success(xwglService.add(xwgl));
    }
    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Xwgl xwgl) throws WebException{
        checkPequest(xwgl);
        Long id = xwgl.getId();
        if(id == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Xwgl xwgl1 = xwglService.get(id);
        if(xwgl1 == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(xwglService.update(xwgl));
    }
    /**
     * 发布
     */
    @ApiOperation(value = "发布")
    @PutMapping("/release/{id}")
    public ResponseUtil release(@PathVariable("id") Long id) throws WebException{
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Xwgl xwgl = xwglService.get(id);
        if(xwgl == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(xwglService.release(id));
    }
    /**
     * 停用
     */
    @ApiOperation(value = "停用")
    @PutMapping("/stop/{id}")
    public ResponseUtil stop(@PathVariable("id") Long id) throws WebException{
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Xwgl xwgl = xwglService.get(id);
        if(xwgl == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(xwglService.out(id));
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
        Xwgl xwgl = xwglService.get(id);
        if(xwgl == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(xwgl);
    }
    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException{
        return ResponseUtil.success(xwglService.delete(id));
    }
    /**
     * 校验参数是否合法
     */
    private void checkPequest(Xwgl xwgl) throws WebException{
        if (xwgl == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
        if (xwgl.getXwbt() == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
        if (xwgl.getXwnr() == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
        if (xwgl.getJgId() == null){
            throw new WebException((ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR));
        }
    }

}

