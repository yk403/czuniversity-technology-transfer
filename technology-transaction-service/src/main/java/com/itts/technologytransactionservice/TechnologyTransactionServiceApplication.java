package com.itts.technologytransactionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.itts.technologytransactionservice.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class TechnologyTransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnologyTransactionServiceApplication.class, args);
    }

}
