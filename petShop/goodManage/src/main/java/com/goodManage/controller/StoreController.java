package com.goodManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goodManage.mapper.StoreMapper;
import com.soft.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/28 15:16
 **/
@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreMapper storeMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getStoreById")
    public Store getStoreById(@RequestParam("storeId") String storeId){
        Store res;
        res = storeMapper.selectById(storeId);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getAllStore")
    public List<Store> getAllStore(){
        return storeMapper.selectList(null);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addStore")
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
    public Boolean deleteStore(@RequestParam("storeId") String storeId){
        Store res;
        res = storeMapper.selectById(storeId);
        if(res==null){
            return false;
        }
        storeMapper.deleteById(storeId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateStore")
    public Boolean updateStore(@RequestBody Store store){
        Store res;
        res = storeMapper.selectById(store.getStoreId());
        if(res==null){
            return false;
        }
        storeMapper.updateById(store);
        return true;
    }
}
