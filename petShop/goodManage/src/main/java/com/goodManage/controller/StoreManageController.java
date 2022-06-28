package com.goodManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goodManage.mapper.StoreMapper;
import com.soft.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/28 15:16
 **/
@RestController
@RequestMapping("/store")
public class StoreManageController {
    @Autowired
    private StoreMapper storeMapper;
    @RequestMapping(method = RequestMethod.GET,value = "/test")
    public String test(){
        String res = "hello world";
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getStoreId")
    public Store getStoreById(@RequestParam("storeId") String storeId){
        Store params = new Store();
        QueryWrapper<Store> wrapper = new QueryWrapper<>(params);
        wrapper.eq("store_id",storeId);
        Store res = storeMapper.selectOne(wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addStore")
    @ResponseBody
    public Boolean addStore(@RequestBody Store store){
        Store res;
        res = storeMapper.selectById(store.getStoreId());
        if(res!=null){
            return false;
        }
        storeMapper.insert(store);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteStore")
    public Boolean deletePetInfo(@RequestParam("storeId") String storeId){
        Store res;
        res = storeMapper.selectById(storeId);
        if(res==null){
            return false;
        }
        storeMapper.deleteById(storeId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateInfo")
    public Boolean updatePetInfo(@RequestBody Store store){
        Store res;
        res = storeMapper.selectById(store.getStoreId());
        if(res==null){
            return false;
        }
        storeMapper.updateById(store);
        return true;
    }
}
