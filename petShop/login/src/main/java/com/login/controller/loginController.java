package com.login.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.login.feign.UserFeignService;
import com.soft.entity.User;
import com.soft.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.POST,value = "/useLogin")
    public Result login(@RequestParam("username") String username,@RequestParam("password") String password){
        Result result = new Result();
        User user = userFeignService.getUserByUsername(username);
        result.setData(user);
        result.success("请求成功");
        return result;
    }

}
