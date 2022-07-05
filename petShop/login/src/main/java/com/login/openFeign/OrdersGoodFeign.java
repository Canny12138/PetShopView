package com.login.openFeign;

import com.soft.entity.OrdersGood;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="userManage-service",path = "/ordersGood",contextId = "login-ordersGood")
public interface OrdersGoodFeign {
    @RequestMapping(method = RequestMethod.POST,value = "/getGoodByOrdersId")
    List<OrdersGood> getGoodByOrdersId(@RequestParam("ordersId") String ordersId);
    @RequestMapping(method = RequestMethod.POST,value = "/getOrdersGoodById")
    OrdersGood getOrdersGoodById(@RequestParam("id") String id);
    @RequestMapping(method = RequestMethod.POST,value = "/addOrdersGood")
    Boolean addOrdersGood(@RequestBody OrdersGood ordersGood);
    @RequestMapping(method = RequestMethod.POST,value = "/deleteOrdersGood")
    Boolean deleteOrdersGood(@RequestParam("id") String id);
    @RequestMapping(method = RequestMethod.POST,value = "/updateOrdersGood")
    Boolean updateOrdersGood(@RequestBody OrdersGood ordersGood);
}
