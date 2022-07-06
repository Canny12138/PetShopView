package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.UserInfo;
import com.userManage.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/6 8:29
 **/
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getUserInfoByUserId")
    public UserInfo getUserInfoByUserId(@RequestParam("userId") String userId){
        UserInfo params = new UserInfo();
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId);
        return userInfoMapper.selectOne(wrapper);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addUserInfo")
    public Boolean addUserInfo(@RequestBody UserInfo userInfo){
        UserInfo res;
        res = userInfoMapper.selectById(userInfo.getInfoId());
        if(res!=null){
            return false;
        }
        userInfoMapper.insert(userInfo);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteUserInfo")
    public Boolean deleteUserInfo(@RequestParam("infoId") String infoId){
        try {
            userInfoMapper.deleteById(infoId);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateUserInfo")
    public Boolean updateUserInfo(@RequestBody UserInfo userInfo){
        try {
            userInfoMapper.updateById(userInfo);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
