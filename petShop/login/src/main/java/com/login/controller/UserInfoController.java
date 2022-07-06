package com.login.controller;

import com.login.openFeign.AddressFeignService;
import com.login.openFeign.UserFeignService;
import com.login.openFeign.UserInfoFeignService;
import com.soft.entity.User;
import com.soft.entity.UserAddress;
import com.soft.entity.UserInfo;
import com.soft.ov.UserInfoOV;
import com.soft.util.JwtUtils;
import com.soft.util.Md5Util;
import com.soft.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/6 12:19
 **/
@RestController
@RequestMapping(method = RequestMethod.POST,value = "/userInfo")
public class UserInfoController {
    @Autowired
    private AddressFeignService addressFeignService;
    @Autowired
    private UserInfoFeignService userInfoFeignService;
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(method = RequestMethod.POST,value = "/getUserInfo")
    public Result getUserInfo(){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        String userId = JwtUtils.getUserIdByToken(token);
        if(!tokenRes.getIsSuccess()||userFeignService.getUserByUserId(userId)==null){
            res.againLogin("未登录");
            return res;
        }
        try {
            User user = userFeignService.getUserByUserId(userId);
            UserInfo userInfo = userInfoFeignService.getUserInfoByUserId(userId);
            String defAddressId = userInfo.getDefAddress();
            UserAddress userAddress = addressFeignService.getAddressByAddressId(defAddressId);
            UserInfoOV userInfoOV = new UserInfoOV(user,userInfo);
            userInfoOV.setDefAddress(userAddress.getAddress());
            res.setData(userInfoOV);
            res.success("获取成功");
        }catch (Exception e){
            res.fail("获取失败");
        }
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateUserImg")
    public Result updateUserImg(@RequestParam("userImg") String userImg){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        String userId = JwtUtils.getUserIdByToken(token);
        if(!tokenRes.getIsSuccess()||userFeignService.getUserByUserId(userId)==null){
            res.againLogin("未登录");
            return res;
        }
        try {
            UserInfo userInfo = userInfoFeignService.getUserInfoByUserId(userId);
            userInfo.setUserImg(userImg);
            userInfoFeignService.updateUserInfo(userInfo);
            res.success("更新成功");
        }catch (Exception e){
            res.fail("更新失败");
        }
        return res;
    }
//    @RequestMapping(method = RequestMethod.POST,value = "/updateUserPassword")
//    public Result updateUserPassword(
//            @RequestParam("oldPassword") String oldPassword,
//            @RequestParam("newPassword") String newPassword
//    ){
//        Result res = new Result();
//        String token = request.getHeader("token");
//        Result tokenRes = JwtUtils.validateToken(token);
//        String userId = JwtUtils.getUserIdByToken(token);
//        if(!tokenRes.getIsSuccess()||userFeignService.getUserByUserId(userId)==null){
//            res.againLogin("未登录");
//            return res;
//        }
//        try {
//            User user = userFeignService.getUserByUserId(userId);
//            user.setPassword(Md5Util.getEncode(newPassword));
//            res.success("更新成功");
//    }
}
