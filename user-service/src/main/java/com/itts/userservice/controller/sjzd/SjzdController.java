package com.itts.userservice.controller.sjzd;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.request.sjzd.AddSjzdRequest;
import com.itts.userservice.request.sjzd.GetSjzdRequest;
import com.itts.userservice.request.sjzd.UpdateSjzdRequest;
import com.itts.userservice.service.sjzd.SjzdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
@Api(tags = "数据字典")
@Slf4j
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/sjzd")
public class SjzdController {

    @Resource
    private SjzdService sjzdService;

    /**
     * 获取数据字典模块列表
     */
    @GetMapping("/models/")
    public ResponseUtil findDictionaryModel(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            @ApiParam(value = "所属模块") @RequestParam(value = "model", required = false) String model,
                                            @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                                            @ApiParam(value = "筛选条件") @RequestParam(value = "condition", required = false) String condition) {


        PageInfo pageInfo = sjzdService.findDictionaryModel(pageNum, pageSize, model, systemType, condition);

        return ResponseUtil.success(pageInfo);
    }

    /**
     * 获取列表
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "所属模块") @RequestParam(value = "model", required = false) String model,
                             @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                             @ApiParam(value = "字典项类型") @RequestParam(value = "dictionary", required = false) String dictionary,
                             @ApiParam(value = "字典编码") @RequestParam(value = "zdbm", required = false) String zdbm,
                             @ApiParam(value = "父级字典ID") @RequestParam(value = "parentId", required = false) Long parentId) {

        PageInfo<Sjzd> byPage = sjzdService.findByPage(pageNum, pageSize, model, systemType, dictionary,zdbm, parentId);
        return ResponseUtil.success(byPage);
    }

    /**
     * 获取数据字典详情
     */
    @GetMapping("/get/")
    @ApiOperation(value = "获取数据字典详情")
    public ResponseUtil get(@ApiParam(value = "所属模块") @RequestParam(value = "model", required = false) String model,
                            @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                            @ApiParam(value = "字典项类型") @RequestParam(value = "dictionary", required = false) String dictionary) {

        GetSjzdRequest result = sjzdService.get(systemType, model, dictionary);

        return ResponseUtil.success(result);
    }

    /**
     * 新增
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody AddSjzdRequest sjzd) throws WebException {

        checkRequest(sjzd);

        AddSjzdRequest add = sjzdService.add(sjzd);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody UpdateSjzdRequest sjzd) throws WebException {

        checkUpdateRequest(sjzd);

        UpdateSjzdRequest update = sjzdService.update(sjzd);
        return ResponseUtil.success(update);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/")
    public ResponseUtil delete(@ApiParam(value = "所属模块") @RequestParam(value = "model", required = false) String model,
                               @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                               @ApiParam(value = "字典项类型") @RequestParam(value = "dictionary", required = false) String dictionary) throws WebException {

        if(StringUtils.isBlank(dictionary)){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        List<Sjzd> sjzds = sjzdService.findBySsmk(systemType, model, dictionary);

        if (!CollectionUtils.isEmpty(sjzds)) {

            for (Sjzd sjzd : sjzds) {

                if (sjzd.getId() == null) {
                    continue;
                }

                sjzdService.delete(sjzd.getId());
            }
        }
        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkRequest(AddSjzdRequest sjzd) throws WebException {

        //如果参数为空，抛出异常
        if (sjzd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(sjzd.getSsmk())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(sjzd.getSsmkmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (CollectionUtils.isEmpty(sjzd.getSjzdItems())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

    /**
     * 校验参数是否合法
     */
    private void checkUpdateRequest(UpdateSjzdRequest sjzd) throws WebException {

        //如果参数为空，抛出异常
        if (sjzd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(sjzd.getSsmk())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(sjzd.getSsmkmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (CollectionUtils.isEmpty(sjzd.getSjzdItems())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

