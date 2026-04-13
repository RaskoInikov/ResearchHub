package com.researchhub.rams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RamsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RamsApplication.class, args);
    }

}
