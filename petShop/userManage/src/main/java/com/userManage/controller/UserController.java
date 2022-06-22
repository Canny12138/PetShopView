package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    @RequestMapping(method = RequestMethod.POST,value = "/getUserByUsername")
    public User getUserByUsername(String username){
        User param = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>(param);
        wrapper.eq("username",username);
        System.out.println(userMapper.selectList(wrapper).get(0));
        return userMapper.selectList(wrapper).get(0);
    }
//    @RequestMapping(method = RequestMethod.POST,value = "/addUser")
//    public Boolean addUser(User user){
//        if(user.getUser_id()==null){
//            user.setUser_id();
//        }
//    }
}
