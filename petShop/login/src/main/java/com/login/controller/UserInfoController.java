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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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
        User user = userFeignService.getUserByUserId(userId);
        if(!tokenRes.getIsSuccess()||user==null){
            res.againLogin("未登录");
            return res;
        }
        try {
//            User user = userFeignService.getUserByUserId(userId);
            UserInfo userInfo = userInfoFeignService.getUserInfoByUserId(userId);
            if(userInfo==null){
                UserInfoOV emptyOV = new UserInfoOV(user);
                res.setData(emptyOV);
            }else {
                String defAddressId = userInfo.getDefAddress();
                UserAddress userAddress = addressFeignService.getAddressByAddressId(defAddressId);
                UserInfoOV userInfoOV = new UserInfoOV(user,userInfo);
                userInfoOV.setDefAddress(userAddress.getAddress());
                res.setData(userInfoOV);
                res.success("获取成功");
            }
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
            if(userInfo==null){
                throw new Exception();
            }
            userInfo.setUserImg(userImg);
            userInfoFeignService.updateUserInfo(userInfo);
            res.success("更新成功");
        }catch (Exception e){
            res.fail("更新失败");
        }
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateUserPassword")
    public Result updateUserPassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword
    ){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        String userId = JwtUtils.getUserIdByToken(token);
        User user = userFeignService.getUserByUserId(userId);
        if(!tokenRes.getIsSuccess()||user==null){
            res.againLogin("未登录");
            return res;
        }
        try {
            if(!Md5Util.getEncode(oldPassword).equals(user.getPassword())){
                res.fail("密码错误");
                throw new Exception();
            }
            user.setPassword(Md5Util.getEncode(newPassword));
            userFeignService.updateUser(user);
            res.success("更新成功");
        }catch (Exception e){
            res.fail("更新失败");
        }
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateUserInfo")
    public Result updateUserInfo(@RequestBody UserInfoOV userInfoOV){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        String userId = JwtUtils.getUserIdByToken(token);
        User user = userFeignService.getUserByUserId(userId);
        if(!tokenRes.getIsSuccess()||user==null){
            res.againLogin("未登录");
            return res;
        }
        try {
            UserInfo userInfo = userInfoFeignService.getUserInfoByUserId(userInfoOV.getUserId());
            if (userInfo==null){
                userInfo = new UserInfo();
                userInfo.setInfoId(UUID.randomUUID().toString());
                userInfo.setUserId(user.getUserId());
                userInfo.setUserImg("/");
                userInfo.setDefAddress("");
                userInfo.setMail(userInfoOV.getMail());
                userInfo.setTel(userInfoOV.getTel());
                userInfoFeignService.addUserInfo(userInfo);
            }
            userInfo.setMail(userInfoOV.getMail());
            userInfo.setTel(userInfoOV.getTel());
            user.setNickname(userInfoOV.getNickname());
            userInfoFeignService.updateUserInfo(userInfo);
            userFeignService.updateUser(user);
            res.success("更改成功");
        }catch (Exception e){
            res.fail("更改失败");
        }
        return res;
    }
}
