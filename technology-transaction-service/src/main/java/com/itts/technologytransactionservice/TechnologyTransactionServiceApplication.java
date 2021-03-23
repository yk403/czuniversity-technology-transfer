package com.itts.technologytransactionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.itts"})
@MapperScan("com.itts.technologytransactionservice.*")
@EnableDiscoveryClient
@EnableFeignClients
public class TechnologyTransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnologyTransactionServiceApplication.class, args);
    }

}
