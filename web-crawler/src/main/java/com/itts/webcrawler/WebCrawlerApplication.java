package com.itts.webcrawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.itts.webcrawler",
        "com.itts.common.exception", "com.itts.common.utils.common", "com.itts.common.config", "com.itts.common.handler"})
@MapperScan("com.itts.webcrawler.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class WebCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebCrawlerApplication.class, args);
    }

}
