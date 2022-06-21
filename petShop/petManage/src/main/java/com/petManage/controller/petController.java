package com.petManage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/20 21:29
 **/
@RestController
@RequestMapping("/pet")
public class petController {
    @Autowired
    RestTemplate restTemplate;
    @RequestMapping("/test")
    public String test(){
        System.out.println("petController");
        //Nacos的服务名
        String res = restTemplate.getForObject("https://login-service/login/test",String.class);
        return "petController"+res;
    }
}
