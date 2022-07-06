package com.login.openFeign;

import com.soft.entity.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name="userManage-service",path = "/userAddress",contextId = "login-address")
public interface AddressFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getAddressByUserId")
    List<UserAddress> getAddressByUserId(@RequestParam("userId") String userId);
    @RequestMapping(method = RequestMethod.POST,value = "/getAddressByAddressId")
    UserAddress getAddressByAddressId(@RequestParam("addressId") String addressId);
    @RequestMapping(method = RequestMethod.POST,value = "/addAddress")
    Boolean addUserAddress(@RequestBody UserAddress userAddress);
    @RequestMapping(method = RequestMethod.POST,value = "/deleteAddress")
    Boolean deleteUserAddress(@RequestParam("addressId") String addressId);
    @RequestMapping(method = RequestMethod.POST,value = "/updateAddress")
    Boolean updateUserAddress(@RequestBody UserAddress userAddress);
}
