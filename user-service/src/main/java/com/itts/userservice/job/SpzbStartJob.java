package com.itts.userservice.job;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.userservice.enmus.VideoStatusEnum;
import com.itts.userservice.mapper.spzb.SpzbMapper;
import com.itts.userservice.model.spzb.Spzb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/26
 */
@Component
@Slf4j
public class SpzbStartJob {

    @Autowired
    private SpzbMapper spzbMapper;

    @Scheduled(cron = "0 */15 * * * ?")
    public void updateSpzbStartStatus(){

        log.info("===============【视频直播】直播状态更新定时器执行===============");

        //查询未开始直播信息
        List<Spzb> spzbs = spzbMapper.selectList(new QueryWrapper<Spzb>().eq("zt", VideoStatusEnum.NOT_START.getKey())
                .eq("sfsc", false));

        if(CollectionUtils.isEmpty(spzbs)){
            log.info("【视频直播】直播状态更新定时器执行, 暂无需要更新直播");
            return;
        }

        log.info("【视频直播】直播状态更新定时器执行，需要更新直播数据：{}", JSONUtil.toJsonStr(spzbs));

        long now = System.currentTimeMillis();

        spzbs.forEach(spzb->{

            if(now >= spzb.getKssj().getTime() && now <= spzb.getJssj().getTime()){

                log.info("【视频直播】直播状态更新定时器执行，更新直播状态为：已开始, 视频直播ID：{}", spzb.getId());

                spzb.setZbmc(VideoStatusEnum.STARTED.getKey());
                spzbMapper.updateById(spzb);
            }
        });

        log.info("===============【视频直播】直播状态更新定时器执行结束===============");

    }
}