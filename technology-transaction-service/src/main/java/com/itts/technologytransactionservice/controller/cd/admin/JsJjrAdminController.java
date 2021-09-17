package com.itts.technologytransactionservice.controller.cd.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.JsJjr;
import com.itts.technologytransactionservice.model.JsJjrDTO;
import com.itts.technologytransactionservice.service.JsJjrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-09-17
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/jsJjr")
@Api(tags = "技术经纪人后台管理")
public class JsJjrAdminController {

    @Resource
    private JsJjrService jsJjrService;

    @GetMapping("/list/")
    @ApiOperation(value = "经纪人列表")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "xslbmcArr",required = false)String xslbmcArr,
                                @RequestParam(value = "fjjgId",required = false)Long jgId,
                                @RequestParam(value = "zsxm",required = false)String zsxm){
        PageInfo<JsJjrDTO> page = jsJjrService.findPage(pageNum, pageSize, xslbmcArr, jgId, zsxm);
        return ResponseUtil.success(page);
    }
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil getOne(@PathVariable Long id){
        JsJjrDTO byJjrId = jsJjrService.getByJjrId(id);
        if(byJjrId == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(byJjrId);
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public ResponseUtil delete(@PathVariable Long id){
        JsJjr byId = jsJjrService.getById(id);
        if(byId ==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        byId.setSfsc(true);
        byId.setGxsj(new Date());
        return ResponseUtil.success(jsJjrService.updateById(byId));
    }
    @PutMapping("/update/")
    @ApiOperation(value = "更新")
    public ResponseUtil update(@RequestBody JsJjr jsJjr){
        JsJjr byId = jsJjrService.getById(jsJjr.getId());
        if (byId == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        BeanUtils.copyProperties(jsJjr,byId);
        byId.setGxsj(new Date());
        return ResponseUtil.success(jsJjrService.updateById(byId));
    }
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody JsJjr jsJjr){
        List<JsJjr> list = jsJjrService.list(new QueryWrapper<JsJjr>().eq("yh_id", jsJjr.getYhId())
                .eq("sfsc", false));
        if (!CollectionUtils.isEmpty(list)){
            throw new WebException(ErrorCodeEnum.USER_EXISTS_ERROR);
        }
        jsJjr.setGxsj(new Date());
        jsJjr.setCjsj(new Date());
        return ResponseUtil.success(jsJjrService.save(jsJjr));
    }
}

