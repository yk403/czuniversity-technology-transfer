package com.itts.userservice.service.spzb.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.userservice.enmus.HuaWeiAssetTypeEnum;
import com.itts.userservice.enmus.HuaWeiTranscodeStatusEnum;
import com.itts.userservice.enmus.VideoEnum;
import com.itts.userservice.mapper.spzb.SpzbMapper;
import com.itts.userservice.model.spzb.Spzb;
import com.itts.userservice.response.thirdparty.GetAssetInfoResponse;
import com.itts.userservice.response.thirdparty.LiveCallBackResponse;
import com.itts.userservice.service.spzb.SpzbService;
import com.itts.userservice.service.spzb.thirdparty.HuaWeiLiveService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频直播 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-27
 */
@Service
public class SpzbServiceImpl extends ServiceImpl<SpzbMapper, Spzb> implements SpzbService {

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
    @Override
    public PageInfo<Spzb> findByPage(Integer pageNum, Integer pageSize, String name, String videoType) {

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper query = new QueryWrapper();
        query.eq("sfsc", false);

        if (StringUtils.isNotBlank(name)) {
            query.eq("zbspmc", name);
        }

        if (StringUtils.isNotBlank(videoType)) {
            query.eq("splx", videoType);
        }

        query.orderByDesc("cjsj");

        List spabs = spzbMapper.selectList(query);

        PageInfo<Spzb> pageInfo = new PageInfo<>(spabs);

        return pageInfo;
    }

    /**
     * 新增
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    public Spzb add(Spzb spzb) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;

        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Date now = new Date();

        if (Objects.equals(spzb.getSplx(), VideoEnum.LIVE_BROADCAST.getCode())) {

            //生成直播密钥
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String code = dateTimeFormatter.format(localDateTime);
            spzb.setZbspmy(VideoEnum.LIVE_BROADCAST.getCode() + "_" + code);
        }

        spzb.setCjr(userId);
        spzb.setGxr(userId);
        spzb.setCjsj(now);
        spzb.setGxsj(now);

        spzbMapper.insert(spzb);

        return spzb;
    }

    /**
     * 更新
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    public Spzb update(Spzb spzb) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;

        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        spzb.setGxr(userId);
        spzb.setGxsj(new Date());

        spzbMapper.updateById(spzb);

        return spzb;
    }

    /**
     * 更新直播数据
     */
    @Override
    public Spzb update(LiveCallBackResponse response) {

        Spzb spzb = spzbMapper.selectOne(new QueryWrapper<Spzb>().eq("zbspmy", response.getStream()));
        if (spzb == null) {
            return null;
        }

        spzb.setXmId(response.getProjectId());
        spzb.setSplx(VideoEnum.LIVE_BROADCAST.getCode());
        spzb.setTlym(response.getPublishDomain());
        spzb.setLzlm(response.getStream());
        spzb.setLzgs(response.getRecordFormat());
        spzb.setMzId(response.getAssetId());
        spzb.setBfdz(response.getPlayUrl());
        spzb.setWxdx(response.getFileSize());
        spzb.setSpsc(response.getRecordDuration());
        spzb.setLzkssj(response.getStartTime());
        spzb.setLzjssj(response.getEndTime());
        spzb.setSpkd(response.getWidth());
        spzb.setSpgd(response.getHeight());
        spzb.setObsqy(response.getObsLocation());
        spzb.setObst(response.getObsBucket());
        spzb.setObslj(response.getObsObject());
        spzb.setMsxx(response.getErrorMessage());

        spzb.setGxsj(new Date());

        //视频转码
        if (StringUtils.isBlank(spzb.getMzId())) {

            log.error("【视频直播完成】录制完成，视频点播转码，媒资ID为空");
        } else {

            String mzId = huaWeiLiveService.dealLive(spzb.getMzId());
            GetAssetInfoResponse result = huaWeiLiveService.getVideoInfo(mzId, HuaWeiAssetTypeEnum.TRANSCODE_INFO.getKey());
            if (result != null) {
                GetAssetInfoResponse.TranscodeInfo transcodeInfo = result.getTranscodeInfo();
                if (transcodeInfo != null && Objects.equals(transcodeInfo.getTranscodeStatus(), HuaWeiTranscodeStatusEnum.TRANSCODE_SUCCEED.getKey())) {

                    List<String> urls = transcodeInfo.getOutput().stream().map(GetAssetInfoResponse.Output::getUrl).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(urls)) {

                        String str = "";
                        for (String url : urls) {

                            str = str + url + ",";
                        }

                        str.substring(0, str.length() - 1);

                        spzb.setBfdz(str);
                    }
                }
            }
        }

        spzbMapper.updateById(spzb);
        return spzb;
    }

    /**
     * 删除
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    public void delete(Spzb spzb) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;

        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        spzb.setGxr(userId);
        spzb.setGxsj(new Date());
        spzb.setSfsc(true);

        spzbMapper.updateById(spzb);
    }
}
