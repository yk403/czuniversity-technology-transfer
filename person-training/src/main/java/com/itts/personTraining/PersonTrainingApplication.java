package com.itts.personTraining;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.itts.personTraining", "com.itts.common.exception",
        "com.itts.common.utils.common", "com.itts.common.config", "com.itts.common.handler"})
@EnableDiscoveryClient
@MapperScan("com.itts.personTraining.mapper")
public class PersonTrainingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonTrainingApplication.class, args);
    }

}
