package com.itts.userservice.service.spzb.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.config.MqConfig;
import com.itts.userservice.dto.KcDTO;
import com.itts.userservice.enmus.HuaWeiAssetTypeEnum;
import com.itts.userservice.enmus.HuaWeiTranscodeStatusEnum;
import com.itts.userservice.enmus.VideoEnum;
import com.itts.userservice.enmus.VideoStatusEnum;
import com.itts.userservice.feign.persontraining.pk.KcRpcService;
import com.itts.userservice.mapper.spzb.SpzbMapper;
import com.itts.userservice.model.spzb.Spzb;
import com.itts.userservice.response.thirdparty.GetAssetInfoResponse;
import com.itts.userservice.response.thirdparty.LiveCallBackResponse;
import com.itts.userservice.service.spzb.SpzbService;
import com.itts.userservice.service.spzb.thirdparty.HuaWeiLiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;

/**
 * <p>
 * 视频直播 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-27
 */
@Service
@Slf4j
public class SpzbServiceImpl extends ServiceImpl<SpzbMapper, Spzb> implements SpzbService {

    @Autowired
    private SpzbMapper spzbMapper;

    @Autowired
    private HuaWeiLiveService huaWeiLiveService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MqConfig mqConfig;

    @Autowired
    private KcRpcService kcRpcService;

    /**
     * 获取列表 - 分页
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    public PageInfo<Spzb> findByPage(Integer pageNum, Integer pageSize, String name, Long courseId, String videoType,Long jgId,String jylx,String xylx) {

        ResponseUtil response = kcRpcService.findByPage(xylx, jylx);
        if(response == null || response.getErrCode().intValue() != 0){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        List<KcDTO> kcDTOList = response.conversionData(new TypeReference<List<KcDTO>>() {
        });
        List<Long> kcIds = kcDTOList.stream().map(KcDTO::getId).collect(Collectors.toList());

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper query = new QueryWrapper();
        query.eq("sfsc", false);

        if (StringUtils.isNotBlank(name)) {
            query.eq("zbspmc", name);
        }

        if (StringUtils.isNotBlank(videoType)) {
            query.eq("splx", videoType);
        }
        if (courseId != null) {
            query.eq("kc_id", courseId);
        } else {
            if (!CollectionUtils.isEmpty(kcIds)) {
                query.in("kc_id", kcIds);
            }else {
                return null;
            }
        }
        if(jgId != null){
            query.eq("jg_id",jgId);
        }else {
            LoginUser loginUser = threadLocal.get();
            Long fjjgId = null;
            if (loginUser != null) {
                fjjgId = loginUser.getFjjgId();
            }
            query.eq(fjjgId != null,"jg_id",fjjgId);
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

        log.info("【视频直播回调】查询视频直播是否存在，直播流名称：{}", response.getStream());
        Spzb spzb = spzbMapper.selectOne(new QueryWrapper<Spzb>().eq("zbspmy", response.getStream()));
        if (spzb == null) {
            return null;
        }
        log.info("【视频直播回调】查询视频直播，直播数据信息：{}", JSONUtil.toJsonStr(spzb));

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
        spzb.setSplx(VideoEnum.RECORDING.getCode());
        spzb.setZt(VideoStatusEnum.FINISHED.getKey());

        spzb.setGxsj(new Date());

        //视频转码
        if (StringUtils.isBlank(spzb.getMzId())) {

            log.error("【视频直播完成】录制完成，视频点播转码，媒资ID为空");
            rabbitTemplate.convertAndSend(mqConfig.getEventExchange(), mqConfig.getVideoReleaseDelayRoutingKey(), response.getAssetId());
        } else {

            try {

                log.info("【视频直播回调】处理视频直播");
                String mzId = huaWeiLiveService.dealLive(spzb);
                log.info("【视频直播回调】处理视频直播完成，媒资ID：{}", mzId);

                //处理成功
                if (StringUtils.isNotBlank(mzId)) {

                    log.info("【视频直播回调】获取华为云媒资信息；媒资ID:{}", mzId);
                    GetAssetInfoResponse result = huaWeiLiveService.getVideoInfo(mzId, HuaWeiAssetTypeEnum.TRANSCODE_INFO.getKey());
                    log.info("【视频直播回调】获取华为云媒资信息, 数据信息：{}", JSONUtil.toJsonStr(result));

                    if (result != null) {
                        GetAssetInfoResponse.TranscodeInfo transcodeInfo = result.getTranscodeInfo();

                        if (transcodeInfo != null) {

                            //转码成功
                            if (Objects.equals(transcodeInfo.getTranscodeStatus(), HuaWeiTranscodeStatusEnum.TRANSCODE_SUCCEED.getKey())) {
                                log.info("【视频直播回调】视频转码完成");
                                List<String> urls = transcodeInfo.getOutput().stream().map(GetAssetInfoResponse.Output::getUrl).collect(Collectors.toList());
                                if (!CollectionUtils.isEmpty(urls)) {

                                    String str = "";
                                    for (String url : urls) {

                                        str = str + url + ",";
                                    }

                                    str.substring(0, str.length() - 1);
                                    log.info("【视频直播回调】视频转码完成, 播放地址：{}", str);
                                    //转码成功设置转码状态为已转码
                                    spzb.setSfzm(1);
                                    spzb.setBfdz(str);
                                }
                            }

                            //转码中、待转码
                            if (Objects.equals(transcodeInfo.getTranscodeStatus(), HuaWeiTranscodeStatusEnum.TRANSCODING.getKey())
                                    || Objects.equals(transcodeInfo.getTranscodeStatus(), HuaWeiTranscodeStatusEnum.WAITING_TRANSCODE.getKey())) {

                                rabbitTemplate.convertAndSend(mqConfig.getEventExchange(), mqConfig.getVideoReleaseDelayRoutingKey(), mzId);
                            }
                        }
                    } else {

                        rabbitTemplate.convertAndSend(mqConfig.getEventExchange(), mqConfig.getVideoReleaseDelayRoutingKey(), mzId);
                    }
                } else {

                    rabbitTemplate.convertAndSend(mqConfig.getEventExchange(), mqConfig.getVideoReleaseDelayRoutingKey(), mzId);
                }
            } catch (Exception e) {

                log.info("【视频直播回调】视频处理异常, {}", e);
            }
        }

        spzbMapper.updateById(spzb);
        log.info("【视频直播回调】视频信息更新完成：{}", JSONUtil.toJsonStr(spzb));
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
