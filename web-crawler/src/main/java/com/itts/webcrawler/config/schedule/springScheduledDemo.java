package com.itts.webcrawler.config.schedule;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class springScheduledDemo {
    //技术交易活动定时到开始时间如果是未开始状态置为进行中
    //测试每分钟，生产每秒
    @Scheduled(cron = "0 0/2 * * * ?")
    public void testScheduled(){
        System.out.println("springScheduled run:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

    }
}
