package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.User;
import com.soft.util.Md5Util;
import com.userManage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public User getUserByUsername(@RequestParam("username") String username){
        User param = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>(param);
        wrapper.eq("username",username);
        List<User> res;
        res = userMapper.selectList(wrapper);
        if(res.size()==0){
            return null;
        }
        return res.get(0);
    }
    @RequestMapping(method = RequestMethod.POST,value = ("/addUser"))
    public Boolean addUser(@RequestBody User user) throws Exception{
        User param = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>(param);
        wrapper.eq("username",user.getUsername());
        List<User> res;
        res = userMapper.selectList(wrapper);
        if(res.size()!=0){
            return false;
        }
        userMapper.insert(user);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = ("/deleteUser"))
    public Boolean deleteUser(@RequestParam("userId") String userId){
        User param = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>(param);
        wrapper.eq("user_id",userId);
        List<User> res;
        res = userMapper.selectList(wrapper);
        if(res.size()==0){
            return false;
        }
        userMapper.deleteById(userId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = ("/updateUser"))
    public Boolean updateUser(@RequestBody User user) throws Exception {
        User param = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>(param);
        wrapper.eq("user_id", user.getUserId());
        List<User> res;
        res = userMapper.selectList(wrapper);
        if(res.size()==0){
            return false;
        }
        user.setPassword(Md5Util.getEncode(user.getPassword()));
        userMapper.updateById(user);
        return true;
    }
}
