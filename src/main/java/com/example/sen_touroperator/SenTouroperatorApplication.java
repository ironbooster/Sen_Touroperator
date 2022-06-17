package com.example.sen_touroperator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableTransactionManagement
public class SenTouroperatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SenTouroperatorApplication.class, args);
    }

}
