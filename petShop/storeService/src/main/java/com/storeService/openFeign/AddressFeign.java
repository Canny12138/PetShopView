package com.storeService.openFeign;

import com.soft.entity.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="goodManage-service",path = "/address",contextId = "store-address")
public interface AddressFeign {
    @RequestMapping(method = RequestMethod.POST,value = "/getAddressById")
    Address getAddressById(@RequestParam("addressId") String addressId);
    @RequestMapping(method = RequestMethod.POST,value = "/addAddress")
    Boolean addAddress(@RequestBody Address Address);
    @RequestMapping(method = RequestMethod.POST,value = "/deleteAddress")
    Boolean deleteAddress(@RequestParam("AddressId") String AddressId);
    @RequestMapping(method = RequestMethod.POST,value = "/updateAddress")
    Boolean updateAddress(@RequestBody Address Address);
}
