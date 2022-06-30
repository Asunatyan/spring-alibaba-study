package com.tuling.mall.sentineldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MallUserSentinelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUserSentinelDemoApplication.class, args);
    }

}
