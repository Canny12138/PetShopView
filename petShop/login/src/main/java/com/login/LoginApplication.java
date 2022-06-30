package com.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/20 21:38
 **/
@SpringBootApplication
@EnableFeignClients
public class LoginApplication {
    public static void main(String[] args){
        SpringApplication.run(LoginApplication.class);
    }
}

