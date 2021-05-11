package com.itts.personTraining;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.itts.personTraining", "com.itts.common.exception",
        "com.itts.common.utils", "com.itts.common.config", "com.itts.common.handler", "com.itts.ittsauthentication"})
@EnableDiscoveryClient
@MapperScan("com.itts.personTraining.mapper")
@EnableFeignClients
public class PersonTrainingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonTrainingApplication.class, args);
    }

}
