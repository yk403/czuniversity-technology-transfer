package com.itts.userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.itts.common.exception", "com.itts.userservice",
                        "com.itts.common.config", "com.itts.ittsauthentication", "com.itts.common.utils.common", "com.itts.common.websocket", "com.itts.common.utils"})
@MapperScan("com.itts.userservice.mapper.*")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
