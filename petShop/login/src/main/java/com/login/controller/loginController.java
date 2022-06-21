package com.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/20 21:36
 **/
@RestController
@RequestMapping("/login")
public class loginController {
    @RequestMapping("/test")
    public String test(){
        System.out.println("loginTest");
        return "loginTest";
    }
}
