package com.itts.ittsauthentication;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itts.ittsauthentication.mapper")
public class IttsAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(IttsAuthenticationApplication.class, args);
    }

}
