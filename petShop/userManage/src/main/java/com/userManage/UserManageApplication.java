package com.userManage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/21 22:12
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.userManage.mapper")
public class UserManageApplication {
    public static void main(String[] args){
        SpringApplication.run(UserManageApplication.class);
    }
}
