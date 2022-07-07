package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.Address;
import com.soft.entity.UserAddress;
import com.userManage.mapper.UserAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/5 20:51
 **/
@RestController
@RequestMapping("/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getAddressByUserId")
    public List<UserAddress> getAddressByUserId(@RequestParam("userId") String userId){
        UserAddress params = new UserAddress();
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId);
        return userAddressMapper.selectList(wrapper);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getAddressByAddressId")
    public UserAddress getAddressByAddressId(@RequestParam("addressId") String addressId){
        return userAddressMapper.selectById(addressId);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addAddress")
    public Boolean addUserAddress(@RequestBody UserAddress userAddress){
        UserAddress res;
        res = userAddressMapper.selectById(userAddress.getAddressId());
        if(res!=null){
            return false;
        }
        userAddressMapper.insert(userAddress);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteAddress")
    public Boolean deleteUserAddress(@RequestParam("addressId") String addressId){
        UserAddress res;
        res = userAddressMapper.selectById(addressId);
        if(res==null){
            return false;
        }
        userAddressMapper.deleteById(addressId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateAddress")
    public Boolean updateUserAddress(@RequestBody UserAddress userAddress){
        UserAddress res;
        res = userAddressMapper.selectById(userAddress.getAddressId());
        if(res==null){
            return false;
        }
        userAddressMapper.updateById(userAddress);
        return true;
    }
}
