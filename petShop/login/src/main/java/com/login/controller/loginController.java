package com.login.controller;

import com.login.feign.UserFeignService;
import com.soft.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/20 21:36
 **/
@RestController
@RequestMapping("/login")
public class loginController {
    @Value("${server.port}")
    String port;

    @Autowired
    UserFeignService userFeignService;

    @RequestMapping("/test")
    public List test(){
        List users = userFeignService.all();
        return users;
    }
    
}
