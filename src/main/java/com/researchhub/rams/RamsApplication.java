package com.researchhub.rams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class RamsApplication {

    private RamsApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(RamsApplication.class, args);
    }

}
