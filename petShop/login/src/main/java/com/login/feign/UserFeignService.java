package com.login.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
    name 调用接口的服务名
    path 调用接口指定的@RequestMapping
 */
@FeignClient(name="userManage-service",path = "/user")
public interface UserFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getUserByUsername")
    public User getUserByUsername(@RequestParam("username") String username);

    @RequestMapping(method = RequestMethod.GET,value = "/all")
    List<User> all();
}
