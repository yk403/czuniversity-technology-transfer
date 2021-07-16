package com.itts.userservice.mqlisterner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.userservice.enmus.HuaWeiAssetTypeEnum;
import com.itts.userservice.enmus.HuaWeiTranscodeStatusEnum;
import com.itts.userservice.mapper.spzb.SpzbMapper;
import com.itts.userservice.model.spzb.Spzb;
import com.itts.userservice.response.thirdparty.GetAssetInfoResponse;
import com.itts.userservice.service.spzb.thirdparty.HuaWeiLiveService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/10
 */
@RabbitListener(queues = "${mqconfig.video_release_queue}")
@Component
@Slf4j
public class VideoMqListerner {

    @Autowired
    private HuaWeiLiveService huaWeiLiveService;

    @Autowired
    private SpzbMapper spzbMapper;

    /**
     * 检查订单是否已支付，如果未支付半小时自动取消订单
     */
    @RabbitHandler
    public void releaseOrderMessage(String mzId, Message message, Channel channel) throws IOException {

        long msgTag = message.getMessageProperties().getDeliveryTag();

        GetAssetInfoResponse response = huaWeiLiveService.getVideoInfo(mzId, HuaWeiAssetTypeEnum.TRANSCODE_INFO.getKey());

        if (response == null) {

            log.info("【华为媒资信息查询】媒资信息不存在， 媒资ID{}", mzId);
            //消费消息
            channel.basicAck(msgTag, false);
            return;
        }

        GetAssetInfoResponse.TranscodeInfo transcodeInfo = response.getTranscodeInfo();
        if (transcodeInfo == null) {
            log.info("【华为媒资信息查询】转码信息不存在， 媒资ID{}", mzId);
            //消费消息
            channel.basicAck(msgTag, false);
            return;
        }

        //转码成功
        if (Objects.equals(transcodeInfo.getTranscodeStatus(), HuaWeiTranscodeStatusEnum.TRANSCODE_SUCCEED.getKey())) {

            Spzb spzb = spzbMapper.selectOne(new QueryWrapper<Spzb>().eq("mz_id", mzId).eq("sfsc", false));
            if (spzb == null) {

                log.info("【视频直播信息查询】视频直播信息不存在， 媒资ID{}", mzId);
                return;
            }

            List<String> urls = transcodeInfo.getOutput().stream().map(GetAssetInfoResponse.Output::getUrl).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(urls)) {

                String str = "";
                for (String url : urls) {

                    str = str + url + ",";
                }

                str.substring(0, str.length() - 1);
                log.info("【视频直播回调】视频转码完成, 播放地址：{}", str);

                spzb.setBfdz(str);
                spzbMapper.updateById(spzb);
                //消费消息
                channel.basicAck(msgTag, false);
            }
        }

        if (Objects.equals(transcodeInfo.getTranscodeStatus(), HuaWeiTranscodeStatusEnum.TRANSCODE_FAILED.getKey())) {

            log.info("【华为媒资信息查询】转码失败， 媒资ID{}", mzId);
            //消费消息
            channel.basicAck(msgTag, false);
            return;
        }

        //未消费，重复消费消息
        channel.basicReject(msgTag, true);
    }
}