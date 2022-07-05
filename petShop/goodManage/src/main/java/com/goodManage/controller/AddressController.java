package com.goodManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goodManage.mapper.AddressMapper;
import com.soft.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 10:20
 **/
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressMapper addressMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getAddressById")
    public Address getAddressById(@RequestParam("addressId") String addressId){
        Address params = new Address();
        QueryWrapper<Address> wrapper = new QueryWrapper<>(params);
        wrapper.eq("address_id",addressId);
        Address res = addressMapper.selectOne(wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addAddress")
    public Boolean addAddress(@RequestBody Address Address){
        Address res;
        res = addressMapper.selectById(Address.getAddressId());
        if(res!=null){
            return false;
        }
        addressMapper.insert(Address);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteAddress")
    public Boolean deleteAddress(@RequestParam("AddressId") String AddressId){
        Address res;
        res = addressMapper.selectById(AddressId);
        if(res==null){
            return false;
        }
        addressMapper.deleteById(AddressId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateAddress")
    public Boolean updateAddress(@RequestBody Address Address){
        Address res;
        res = addressMapper.selectById(Address.getAddressId());
        if(res==null){
            return false;
        }
        addressMapper.updateById(Address);
        return true;
    }
}
