package com.userManage.controller;

import com.soft.entity.User;
import com.userManage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/21 22:09
 **/

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping(method = RequestMethod.GET,value = "/all")
    public List<User> all(){
        return userMapper.selectList(null);
    }
}
