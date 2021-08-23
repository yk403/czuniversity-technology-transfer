package com.itts.userservice.controller.spzb.admin;


import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.enmus.HuaWeiAssetTypeEnum;
import com.itts.userservice.enmus.HuaWeiTranscodeStatusEnum;
import com.itts.userservice.mapper.spzb.SpzbMapper;
import com.itts.userservice.model.spzb.Spzb;
import com.itts.userservice.response.thirdparty.GetAssetInfoResponse;
import com.itts.userservice.response.thirdparty.LiveCallBackResponse;
import com.itts.userservice.service.spzb.SpzbService;
import com.itts.userservice.service.spzb.thirdparty.HuaWeiLiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频直播 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-27
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/spzb")
@Api(tags = "视频直播")
public class SpzbAdminController {

    @Autowired
    private SpzbService spzbService;
    @Autowired
    private SpzbMapper spzbMapper;
    @Autowired
    private HuaWeiLiveService huaWeiLiveService;

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
                             @ApiParam(value = "课程ID") @RequestParam(value = "courseId", required = false) Long courseId,
                             @ApiParam(value = "视频类型： live_broadcast - 直播；recording - 录播") @RequestParam(value = "videoType", required = false) String videoType) {

        PageInfo pageInfo = spzbService.findByPage(pageNum, pageSize, name, courseId, videoType);

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

        if (spzb == null) {
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
    /***
    * @Description: 华为云自动转码失败转手动转码接口
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/8/23
    */
    @PutMapping("/transcode/")
    @ApiOperation(value = "视频转码")
    public ResponseUtil transcode(@RequestBody Spzb spzb) {

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

        //Spzb result = spzbService.update(spzb);
        try {
            log.info("【视频直播转码】视频直播转码");
            String mzId=huaWeiLiveService.dealLive(checkResult);
            log.info("【视频直播转码】视频直播转码完成，媒资ID：{}", mzId);
            //处理成功
            if (StringUtils.isNotBlank(mzId)) {
                log.info("【视频直播转码】获取华为云媒资信息；媒资ID:{}", mzId);
                GetAssetInfoResponse result = huaWeiLiveService.getVideoInfo(mzId, HuaWeiAssetTypeEnum.TRANSCODE_INFO.getKey());
                log.info("【视频直播转码】获取华为云媒资信息, 数据信息：{}", JSONUtil.toJsonStr(result));
                if (result != null) {
                    GetAssetInfoResponse.TranscodeInfo transcodeInfo=result.getTranscodeInfo();
                    if (transcodeInfo != null){
                        //转码成功
                        if (Objects.equals(transcodeInfo.getTranscodeStatus(), HuaWeiTranscodeStatusEnum.TRANSCODE_SUCCEED.getKey())) {
                            log.info("【视频直播转码】视频转码完成");
                            List<String> urls = transcodeInfo.getOutput().stream().map(GetAssetInfoResponse.Output::getUrl).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(urls)) {

                                String str = "";
                                for (String url : urls) {

                                    str = str + url + ",";
                                }

                                str.substring(0, str.length() - 1);
                                log.info("【视频直播转码】视频转码完成, 播放地址：{}", str);
                                //转码成功设置转码状态为已转码
                                spzb.setSfzm(1);
                                spzb.setBfdz(str);
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            log.info("【视频直播转码】视频转码失败");
            throw new WebException(ErrorCodeEnum.HUA_WEI_YUN_DEAL_VIDEO_ERROR);
        }
        spzbMapper.updateById(spzb);
        log.info("【视频直播转码】视频信息更新完成：{}", JSONUtil.toJsonStr(spzb));
        return ResponseUtil.success("转码成功");
    }
    /**
     * 视频录制完成回调接口
     */
    @PostMapping("/callback/")
    public ResponseUtil callback(@RequestBody String responseStr) {

        log.info("【视频直播回调】响应数据：{}", responseStr);

        if(StringUtils.isBlank(responseStr)){
            throw new WebException(ErrorCodeEnum.HUA_WEI_YUN_VIDEO_CALLBACK_ERROR);
        }

        LiveCallBackResponse response = JSONUtil.toBean(JSONUtil.parseObj(responseStr), LiveCallBackResponse.class);

        if(response == null){
            throw new WebException(ErrorCodeEnum.HUA_WEI_YUN_VIDEO_CALLBACK_ERROR);
        }

        Spzb spzb = spzbService.update(response);

        return ResponseUtil.success(spzb);
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

        if(spzb.getKssj().getTime() >= spzb.getJssj().getTime()){
            throw new WebException(ErrorCodeEnum.USER_VIDEO_DATE_ERROR);
        }

        if (StringUtils.isBlank(spzb.getZbmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

