package com.itts.authorition;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.itts.authorition", "com.itts.common.exception",
        "com.itts.common.utils.common", "com.itts.common.config"})
@MapperScan("com.itts.authorition.mapper")
public class AuthoritionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthoritionApplication.class, args);
    }

}
