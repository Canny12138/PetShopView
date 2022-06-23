package com.login.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.login.feign.UserFeignService;
import com.soft.entity.User;
import com.soft.util.JwtUtils;
import com.soft.util.Md5Util;
import com.soft.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    /*
        登录
     */
    @RequestMapping(method = RequestMethod.POST,value = "/useLogin")
    public Result login(@RequestParam("username") String username,@RequestParam("password") String password) throws Exception {
        Result result = new Result();
        User user = userFeignService.getUserByUsername(username);
        if(user==null){
            result.fail("用户不存在");
            return result;
        }
        password = Md5Util.getEncode(password);
        if(!user.getPassword().equals(password)){
            result.fail("密码错误");
            return result;
        }
        String token = JwtUtils.getToken(user);
        result.setData(token);
        result.success("登录成功");
        return result;
    }
    /*
        注册
     */
    @RequestMapping(method = RequestMethod.POST,value = "/register")
    public Result register(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("nickname") String nickname) throws Exception{
        User user = new User();
        Result result = new Result();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setUserVip(0);
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(Md5Util.getEncode(password));
        System.out.println(user);
        if(userFeignService.addUser(user)){
            result.success("注册成功");
        }else {
            result.fail("注册失败");
        }
        return result;
    }

}
