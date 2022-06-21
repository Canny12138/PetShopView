package com.petManage.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/20 21:29
 **/
@RestController
@RequestMapping("/pet")
public class petController {

    @RequestMapping("/test")
    public String test(){
        System.out.println("petController");
        return "petController";
    }
}
