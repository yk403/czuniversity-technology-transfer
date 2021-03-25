package com.itts.authorition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.itts.authorition", "com.itts.common.exception", "com.itts.userservice"})
public class AuthoritionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthoritionApplication.class, args);
    }

}
