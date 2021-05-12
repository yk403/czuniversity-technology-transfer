package com.itts.userservice.controller.spzb.admin;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.spzb.Spzb;
import com.itts.userservice.service.spzb.SpzbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 视频直播 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-27
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/spzb")
@Api(tags = "视频直播")
public class SpzbAdminController {

    @Autowired
    private SpzbService spzbService;

    /**
     * 获取列表 - 分页
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表 - 分页")
    public ResponseUtil list(@ApiParam(value = "当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "直播视频名称") @RequestParam(value = "name", required = false) String name,
                             @ApiParam(value = "视频类型： live_broadcast - 直播；recording - 录播") @RequestParam(value = "videoType", required = false) String videoType) {

        PageInfo pageInfo = spzbService.findByPage(pageNum, pageSize, name, videoType);

        return ResponseUtil.success(pageInfo);
    }

    /**
     * 获取详情
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@ApiParam("主键ID") @PathVariable("id") Long id) {

        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        Spzb spzb = spzbService.getById(id);

        if(spzb == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (spzb.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(spzb);
    }

    /**
     * 新增
     *
     * @param
     * @return
     * @author liuyingming
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody Spzb spzb) {

        checkSpzb(spzb);

        Spzb result = spzbService.add(spzb);

        return ResponseUtil.success(result);
    }

    /**
     * 更新
     *
     * @param
     * @return
     * @author liuyingming
     */
    @PutMapping("/update/")
    @ApiOperation(value = "更新")
    public ResponseUtil update(@RequestBody Spzb spzb) {

        if (spzb.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        checkSpzb(spzb);

        Spzb checkResult = spzbService.getById(spzb.getId());
        if (checkResult == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (checkResult.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        Spzb result = spzbService.update(spzb);

        return ResponseUtil.success(result);
    }

    /**
     * 删除
     *
     * @param
     * @return
     * @author liuyingming
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public ResponseUtil delete(@PathVariable("id") Long id) {

        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        Spzb checkResult = spzbService.getById(id);
        if (checkResult == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (checkResult.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        spzbService.delete(checkResult);

        return ResponseUtil.success();
    }

    /**
    *视频录制完成回调接口
    */
    @PostMapping("/callback/")
    public ResponseUtil callback(@RequestBody String string){

        System.out.println(string);

        return ResponseUtil.success(string);
    }

    /**
     * 校验参数
     *
     * @param
     * @return
     * @author liuyingming
     */
    private void checkSpzb(Spzb spzb) {

        if (spzb == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(spzb.getZbmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

