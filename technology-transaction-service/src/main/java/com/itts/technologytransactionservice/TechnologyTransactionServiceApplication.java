package com.itts.technologytransactionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.itts.technologytransactionservice",
        "com.itts.common.exception", "com.itts.ittsauthentication", "com.itts.common.utils", "com.itts.common.config", "com.itts.common.websocket", "com.itts.common.handler"})
@MapperScan("com.itts.technologytransactionservice.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class TechnologyTransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnologyTransactionServiceApplication.class, args);
    }

}
