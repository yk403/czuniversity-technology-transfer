package com.itts.personTraining.controller.sjpz.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.sjpz.Sjpz;
import com.itts.personTraining.service.sjpz.SjpzService;
import com.itts.personTraining.vo.sjpz.SjpzVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-09-07
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/sjpz")
@Api(tags = "试卷后台配置")
public class SjpzController {

    @Resource
    private SjpzService sjpzService;

    @GetMapping("/list/")
    @ApiModelProperty(value = "查询批次列表")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "fjjgId", required = false) Long fjjgId,
                                @RequestParam(value = "nd", required = false) String nd,
                                @RequestParam(value = "mc", required = false) String mc){
        return ResponseUtil.success(sjpzService.getList(pageNum,pageSize,fjjgId, nd, mc));
    }

    /**
     * 根据id获取
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取")
    public ResponseUtil getByOne(@PathVariable("id")Long id){
        SjpzVO sjpzVO = sjpzService.get(id);
        return ResponseUtil.success(sjpzVO);
    }
    /**
     * 新增
     * @param
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody SjpzVO sjpzVO)throws WebException{
        SjpzVO add = sjpzService.add(sjpzVO);
        return ResponseUtil.success(add);
    }
    @ApiOperation(value = "更新")
    @PutMapping("/update")
    public ResponseUtil update(@RequestBody SjpzVO sjpzVO)throws WebException{
        Sjpz old = sjpzService.getById(sjpzVO.getId());
        if(old == null){
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        SjpzVO update = sjpzService.update(old,sjpzVO);
        return ResponseUtil.success(update);
    }
    /**
     * 删除
     */
    @ApiOperation(value ="删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        return ResponseUtil.success(sjpzService.delete(id));
    }
}

