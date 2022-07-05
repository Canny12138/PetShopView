package com.storeService.openFeign;

import com.soft.entity.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="goodManage-service",path = "/store")
public interface StoreFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getStoreById")
    Store getStoreById(@RequestParam("storeId") String storeId);

    @RequestMapping(method = RequestMethod.POST,value = "/addStore")
    Boolean addStore(@RequestBody Store store);

    @RequestMapping(method = RequestMethod.POST,value = "/deleteStore")
    Boolean deletePetInfo(@RequestParam("storeId") String storeId);

    @RequestMapping(method = RequestMethod.POST,value = "/updateStore")
    Boolean updatePetInfo(@RequestBody Store store);

    @RequestMapping(method = RequestMethod.POST,value = "/getAllStore")
    List<Store> getAllStore();
}
