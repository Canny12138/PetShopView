package com.storeService.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.soft.entity.Address;
import com.soft.entity.Store;
import com.soft.ov.StoreInfoOV;
import com.soft.ov.StoreOV;
import com.soft.util.Result;
import com.storeService.openFeign.AddressFeign;
import com.storeService.openFeign.StoreFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/6 21:19
 **/
@RestController
@RequestMapping("/storeInfoOV")
public class StoreInfoOVController {
    @Autowired
    private StoreFeignService storeFeignService;
    @Autowired
    private AddressFeign addressFeign;
    @RequestMapping(method = RequestMethod.POST,value = "/getAllStoreInfoOV")
    public Result getAllStoreInfoOV() {
        Result res = new Result();
        try {
            List<Store> stores = storeFeignService.getAllStore();
            if (stores.size() == 0) {
                res.fail("未找到商店");
            }
            List<StoreInfoOV> resData = new LinkedList<>();
            for (Store store : stores) {
                Address address = addressFeign.getAddressById(store.getAddressId());
                StoreInfoOV storeInfoOV = new StoreInfoOV(store, address);
                resData.add(storeInfoOV);
            }
            res.setData(resData);
        } catch (Exception e) {
            res.fail("获取失败");
        }
        res.success("获取成功");
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getStoreInfoOVById")
    public Result getStoreInfoOVById(@RequestParam("storeId") String storeId){
        Result res = new Result();
        try {
            Store store = storeFeignService.getStoreById(storeId);
            Address address = addressFeign.getAddressById(store.getAddressId());
            StoreInfoOV storeInfoOV = new StoreInfoOV(store,address);
            res.setData(storeInfoOV);
            res.success("获取成功");
        }catch (Exception e){
            res.fail("获取失败");
        }
        return res;
    }
}
