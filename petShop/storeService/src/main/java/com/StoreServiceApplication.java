package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 10:29
 **/
@SpringBootApplication
@EnableFeignClients
public class StoreServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class);
    }
}
