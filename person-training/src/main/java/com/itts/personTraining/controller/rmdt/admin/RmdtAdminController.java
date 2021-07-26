package com.itts.personTraining.controller.rmdt.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.gngl.Gngl;
import com.itts.personTraining.model.rmdt.Rmdt;
import com.itts.personTraining.service.rmdt.RmdtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-26
 */
@RestController
@RequestMapping(ADMIN_BASE_URL +"/v1/rmdt")
@Api(tags="热门动态")
public class RmdtAdminController {

    @Resource
    private RmdtService rmdtService;

    @GetMapping("/list/")
    @ApiOperation(value = "查询功能")
    public ResponseUtil getList(@RequestParam("jgId") Long jgId){

        List<Rmdt> list = rmdtService.list(new QueryWrapper<Rmdt>().eq(jgId != null, "jg_id", jgId)
        .orderByAsc("px"));

        return ResponseUtil.success(list);
    }
    @ApiOperation(value = "使用")
    @PutMapping("/use/{id}")
    public ResponseUtil use(@PathVariable("id") Long id)throws WebException {
        if(id == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Rmdt rmdt = rmdtService.getById(id);
        if(rmdt == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(rmdtService.use(id));
    }
    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody Rmdt rmdt)throws WebException{
        return ResponseUtil.success(rmdtService.save(rmdt));
    }
}

