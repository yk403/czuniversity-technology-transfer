package com.itts.userservice.controller.js;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.ResponseUtil;
import com.itts.userservice.TJs.model.TJs;
import com.itts.userservice.TJs.service.TJsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
@Api(tags="角色管理")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL+"/tJs")
public class TJsController {

    @Resource
    private TJsService tJsService;

    /**
     * 获取列表
     */
    @ApiOperation(value="获取列表")
    @GetMapping("/list/")
    public ResponseUtil find(@RequestParam(value="pageNum",defaultValue = "1")Integer pageNum,
                             @RequestParam(value="pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<TJs> byPage = tJsService.findByPage(pageNum, pageSize);
        return ResponseUtil.success(byPage);
    }

    /**
     * 获取详情
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id")Long id){
        TJs tJs = tJsService.get(id);
        return ResponseUtil.success(tJs);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody TJs tJs)throws WebException {
        checkRequst(tJs);
        TJs add = tJsService.add(tJs);
        return ResponseUtil.success(add);
    }
    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/{id}")
    public ResponseUtil update(@PathVariable("id")Long id,@RequestBody TJs tJs){
        //检查参数是否合法
        if(id==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        TJs tJs1 = tJsService.get(id);
        if(tJs1==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        checkRequst(tJs);
        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(tJs,tJs1,"id","chsj","cjr");
        tJsService.update(tJs1);
        return ResponseUtil.success(tJs1);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id")Long id)throws WebException{
        if(id==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        TJs tJs = tJsService.get(id);
        if(tJs==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        //设置删除状态，更新删除时间
        tJs.setSfsc(true);
        tJs.setCjsj(new Date());
        tJsService.update(tJs);
        return ResponseUtil.success();
    }

    /**
     * 校验参数
     */
    private void checkRequst(TJs tJs)throws WebException{
        if(tJs==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

