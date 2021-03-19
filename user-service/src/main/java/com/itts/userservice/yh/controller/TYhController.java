package com.itts.userservice.yh.controller;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.ResponseUtil;
import com.itts.userservice.yh.mapper.TYhMapper;
import com.itts.userservice.yh.model.TYh;
import com.itts.userservice.yh.service.TYhService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
@Api(tags="用户管理")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL+"/yh")
public class TYhController {

    @Resource
    private TYhService tYhService;

    /**
     * 获取列表
     * @param pageNum pageSize
     * @author fl
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil find(@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value="pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<TYh> byPage = tYhService.findByPage(pageNum, pageSize);
        return ResponseUtil.success(byPage);
    }

    /**
     * 获取详情
     * @param id
     * @author fl
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get (@PathVariable("id")Long id){
        TYh tYh = tYhService.get(id);
        return ResponseUtil.success(tYh);
    }

    /**
     * 新增
     * @author fl
     *
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody TYh tYh)throws WebException{
        //检查参数是否合法
        checkPequest(tYh);
        TYh add = tYhService.add(tYh);
        return ResponseUtil.success(add);
    }
    /**
     * 更新
     * @author fl
     *
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/{id}")
    public ResponseUtil update(@PathVariable("id")Long id,@RequestBody TYh tYh)throws WebException{
        //检查参数是否合法
        if(id==null){
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_NOT_FIND_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        TYh tYh1 = tYhService.get(id);
        if(tYh1==null){
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_NOT_FIND_ERROR);
        }
        //检查参数是否合法
        checkPequest(tYh);
        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(tYh,tYh1,"id","chsj","cjr");
        tYhService.update(tYh1);
        return ResponseUtil.success(tYh1);

    }
    /**
     * 删除
     * @author fl
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id")Long id)throws WebException{
        //检查参数是否为空
        if(id==null){
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        TYh yh = tYhService.get(id);

        if(yh == null){
            throw new WebException(-1, "数据不存在");
        }
        //设置删除状态，更新删除时间
        yh.setSfsc(true);
        yh.setGxsj(new Date());
        //更新
        tYhService.update(yh);

        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkPequest(TYh tYh) throws WebException {
        //如果参数为空，抛出异常
        if(tYh==null){
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_NOT_FIND_ERROR);
        }
    }
}

