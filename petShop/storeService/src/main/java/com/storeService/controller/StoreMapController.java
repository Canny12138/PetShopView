package com.storeService.controller;

import com.soft.entity.Address;
import com.soft.entity.Store;
import com.soft.ov.StoreMapOV;
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
 * Create time:2022/7/5 15:57
 **/
@RestController
@RequestMapping("/storeMap")
public class StoreMapController {
    @Autowired
    StoreFeignService storeFeignService;
    @Autowired
    AddressFeign addressFeign;
    @RequestMapping(method = RequestMethod.POST,value = "/getAllStoreInMap")
    public Result getAllStoreInMap(){
        Result result = new Result();
        List<StoreMapOV> resData = new LinkedList<>();
        try {
            List<Store> storeList = storeFeignService.getAllStore();
            for(Store store:storeList){
                StoreMapOV storeMapOV = new StoreMapOV(store);
                Address address = addressFeign.getAddressById(store.getAddressId());
                storeMapOV.setLongitude(address.getLongitude());
                storeMapOV.setLatitude(address.getLatitude());
                resData.add(storeMapOV);
            }
        }catch (Exception e){
            result.fail("请求失败");
            return result;
        }

        result.setData(resData);
        return result;
    }
}
