package com.login.openFeign;

import com.soft.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/6 11:26
 **/
@FeignClient(name="userManage-service",path = "/userInfo",contextId = "login-userInfo")
public interface UserInfoFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getUserInfoByUserId")
    UserInfo getUserInfoByUserId(@RequestParam("userId") String userId);
    @RequestMapping(method = RequestMethod.POST,value = "/addUserInfo")
    Boolean addUserInfo(@RequestBody UserInfo userInfo);
    @RequestMapping(method = RequestMethod.POST,value = "/deleteUserInfo")
    Boolean deleteUserInfo(@RequestParam("infoId") String infoId);
    @RequestMapping(method = RequestMethod.POST,value = "/updateUserInfo")
    Boolean updateUserInfo(@RequestBody UserInfo userInfo);
}
