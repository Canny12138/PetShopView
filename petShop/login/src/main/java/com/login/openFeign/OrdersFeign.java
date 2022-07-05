package com.login.openFeign;

import com.soft.entity.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="userManage-service",path = "/orders",contextId = "login-orders")
public interface OrdersFeign {
    @RequestMapping(method = RequestMethod.POST,value = "/getOrdersByUserId")
    List<Orders> getOrdersByUserId(@RequestParam("userId") String userId);
    @RequestMapping(method = RequestMethod.POST,value = "/getOrdersById")
    Orders getOrdersById(@RequestParam("ordersId") String ordersId);
    @RequestMapping(method = RequestMethod.POST,value = "/addOrders")
    Boolean addOrders(@RequestBody Orders orders);
    @RequestMapping(method = RequestMethod.POST,value = "/deleteOrders")
    Boolean deleteOrders(@RequestParam("ordersId") String ordersId);
    @RequestMapping(method = RequestMethod.POST,value = "/updateOrders")
    Boolean updateOrders(@RequestBody Orders orders);
}
