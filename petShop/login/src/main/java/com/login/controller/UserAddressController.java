package com.login.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.login.openFeign.AddressFeignService;
import com.login.openFeign.UserFeignService;
import com.login.openFeign.UserInfoFeignService;
import com.soft.entity.Address;
import com.soft.entity.User;
import com.soft.entity.UserAddress;
import com.soft.entity.UserInfo;
import com.soft.ov.UserAddressOV;
import com.soft.util.JwtUtils;
import com.soft.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/6 22:14
 **/
@RestController
@RequestMapping("/userAddress")
public class UserAddressController {
    @Autowired
    AddressFeignService addressFeignService;
    @Autowired
    UserFeignService userFeignService;
    @Autowired
    UserInfoFeignService userInfoFeignService;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(method = RequestMethod.POST,value = "/getDefAddress")
    public Result getDefAddress(){
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
            UserInfo userInfo = userInfoFeignService.getUserInfoByUserId(userId);
            if(userInfo==null){
                throw new Exception();
            }
            UserAddress userAddress = addressFeignService.getAddressByAddressId(userInfo.getDefAddress());
            if(userAddress==null){
                throw new Exception();
            }
            UserAddressOV userAddressOV = new UserAddressOV(userAddress);
            userAddressOV.setIsDef(1);
            res.setData(userAddressOV);
            res.success("请求成功");
        }catch (Exception e){
            res.fail("无默认地址");
        }
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getAddress")
    public Result getAddress(){
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
            String defAddressId = userInfoFeignService.getUserInfoByUserId(userId).getDefAddress();
            List<UserAddressOV> resData = new LinkedList<>();
            List<UserAddress> userAddressList = addressFeignService.getAddressByUserId(userId);
            if(userAddressList.size()==0){
                throw new Exception();
            }
            for(UserAddress userAddress:userAddressList){
                UserAddressOV userAddressOV = new UserAddressOV(userAddress);
                if(userAddressOV.getAddressId().equals(defAddressId)){
                    userAddressOV.setIsDef(1);
                }
                resData.add(userAddressOV);
            }
            res.setData(resData);
            res.success("请求成功");
        }catch (Exception e){
            res.fail("暂无地址");
        }
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addAddress")
    public Result addAddress(@RequestParam("address") String address,
                             @RequestParam("receiver") String receiver,
                             @RequestParam("tel") String tel
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
            UserAddress userAddress = new UserAddress();
            userAddress.setAddressId(UUID.randomUUID().toString());
            userAddress.setAddress(address);
            userAddress.setUserId(userId);
            userAddress.setReceiver(receiver);
            userAddress.setTel(tel);
            addressFeignService.addUserAddress(userAddress);
            res.success("添加成功");
        }catch (Exception e){
            res.fail("添加失败");
        }
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateAddress")
    public Result updateAddress(@RequestParam("addressId") String addressId,
                                @RequestParam("address") String address,
                                @RequestParam("receiver") String receiver,
                                @RequestParam("tel") String tel
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
            UserAddress userAddress = addressFeignService.getAddressByAddressId(addressId);
            userAddress.setAddress(address);
            userAddress.setReceiver(receiver);
            userAddress.setTel(tel);
            addressFeignService.updateUserAddress(userAddress);
            res.success("添加成功");
        }catch (Exception e){
            res.fail("添加失败");
        }
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/setDefAddress")
    public Result setDefAddress(@RequestParam("addressId") String addressId){
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
            UserInfo userInfo = userInfoFeignService.getUserInfoByUserId(userId);
            if(userInfo==null){
                throw new Exception();
            }
            userInfo.setDefAddress(addressId);
            userInfoFeignService.updateUserInfo(userInfo);
            res.success("设置成功");
        }catch (Exception e){
            res.fail("设置失败");
        }
        return res;
    }
}
