package com.storeService.controller;

import com.soft.entity.Address;
import com.soft.entity.Store;
import com.soft.ov.StoreOV;
import com.soft.util.Result;
import com.storeService.openFeign.AddressFeign;
import com.storeService.openFeign.StoreFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/6 21:07
 **/
@RestController
@RequestMapping("/storeOV")
public class StoreOVController {
    @Autowired
    private StoreFeignService storeFeignService;
    @Autowired
    private AddressFeign addressFeign;
    @RequestMapping(method = RequestMethod.POST,value = "/getAllStoreOV")
    public Result getAllStoreOV(){
        Result res = new Result();
        try {
            List<Store> stores = storeFeignService.getAllStore();
            if(stores.size()==0){
                res.fail("未找到商店");
            }
            List<StoreOV> resData = new LinkedList<>();
            for(Store store:stores){
                Address address = addressFeign.getAddressById(store.getAddressId());
                StoreOV storeOV = new StoreOV(store,address);
                resData.add(storeOV);
            }
            res.setData(resData);
        }catch (Exception e){
            res.fail("获取失败");
        }
        res.success("获取成功");
        return res;

    }


}
