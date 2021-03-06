package com.itts.webcrawler.config.schedule;


import com.itts.webcrawler.mapper.TJsspMapper;
import com.itts.webcrawler.utils.JszyfwRepoPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class springScheduledDemo {
    @Autowired
    private JszyfwRepoPageProcessor jszyfwRepoPageProcessor;
    @Autowired
    private TJsspMapper tJsspMapper;
    //技术交易活动定时到开始时间如果是未开始状态置为进行中
    //测试每10分钟，生产每1天
    //@Scheduled(cron = "0 0/10 * * * ?")
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void testScheduled(){
        tJsspMapper.deleteJssp();
        System.out.println("springScheduled run:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        Spider.create(jszyfwRepoPageProcessor).addUrl("https://www.jszyfw.com/patent/").thread(5).run();
    }
}
