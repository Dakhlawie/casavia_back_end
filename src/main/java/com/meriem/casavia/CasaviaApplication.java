package com.meriem.casavia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CasaviaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasaviaApplication.class, args);
    }

}
