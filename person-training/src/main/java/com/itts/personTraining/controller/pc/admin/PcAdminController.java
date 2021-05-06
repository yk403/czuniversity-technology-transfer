package com.itts.personTraining.controller.pc.admin;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.service.pc.PcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;

/**
 * <p>
 * 批次表 前端控制器
 * </p>
 *
 * @author FL
 * @since 2021-04-20
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL+"/v1/pc")
@Api(value = "PcAdminController", tags = "批次后台管理")
public class PcAdminController {

    @Resource
    private PcService pcService;

    @GetMapping("/list/")
    @ApiModelProperty(value = "查询批次列表")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        PageInfo<Pc> byPage = pcService.findByPage(pageNum, pageSize);
        return ResponseUtil.success(byPage);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil getByOne(@PathVariable("id")Long id){
        Pc pc = pcService.get(id);
        return ResponseUtil.success(pc);
    }

    /**
     * 根据批次类型查询批次信息
     * @param pclx
     * @return
     */
    @GetMapping("/getByPclx/{pclx}")
    @ApiOperation(value = "根据批次类型查询批次信息")
    public ResponseUtil getByPclx(@PathVariable("pclx")String pclx){
        if (pclx == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        return ResponseUtil.success(pcService.getByPclx(pclx));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Pc pc)throws WebException{
        checkRequset(pc);
        Pc add = pcService.add(pc);
        return ResponseUtil.success(add);
    }
    @ApiOperation(value = "更新")
    @PutMapping("/update")
    public ResponseUtil update(@RequestBody Pc pc)throws WebException{
        checkRequset(pc);
        Pc old = pcService.get(pc.getId());
        if(old==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        BeanUtils.copyProperties(pc,old,"id","cjsj","cjr");
        pcService.update(old);
        return ResponseUtil.success(old);
    }
    /**
     * 删除
     */
    @ApiOperation(value ="删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        if(id==null){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Pc pc = pcService.get(id);
        if(pc==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        pc.setSfsc(true);
        Pc update = pcService.update(pc);
        return ResponseUtil.success(update);
    }
    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/deleteBatch/")
    public ResponseUtil deleteBatch(@RequestBody List ids)throws WebException{
        if(ids==null){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Boolean aBoolean = pcService.updateBatch(ids);
        return ResponseUtil.success(aBoolean);
    }
    /**
     * 校验参数是否合法
     */
    private void checkRequset(Pc pc)throws WebException{
        if(pc==null){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if(pc.getPcmc()==null){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if(pc.getPch()==null){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if(pc.getPclx()==null){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

