package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.User;
import com.soft.util.Md5Util;
import com.userManage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public User getUserByUsername(@RequestParam("username") String username){
        User param = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>(param);
        wrapper.eq("username",username);
        User res;
        res = userMapper.selectOne(wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addUser")
    public Boolean addUser(@RequestBody User user) throws Exception{
        User param = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>(param);
        wrapper.eq("username",user.getUsername());
        User res;
        res = userMapper.selectOne(wrapper);
        if(res!=null){
            return false;
        }
        userMapper.insert(user);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteUser")
    public Boolean deleteUser(@RequestParam("userId") String userId){
        User res;
        res = userMapper.selectById(userId);
        if(res==null){
            return false;
        }
        userMapper.deleteById(userId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updatePassword")
    public Boolean updatePassword(@RequestBody User user) throws Exception {
        User res;
        res = userMapper.selectById(user.getUserId());
        if(res==null){
            return false;
        }
        res.setPassword(Md5Util.getEncode(user.getPassword()));
        userMapper.updateById(res);
        return true;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/updateUser")
    public Boolean updateUser(@RequestBody User user) {
        User res = userMapper.selectById(user.getUserId());
        if(res==null){
            return false;
        }
        userMapper.updateById(user);
        return true;
    }
}
