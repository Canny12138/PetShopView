package com.login.openFeign;

import com.soft.entity.OrdersStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="userManage-service",path = "/ordersStatus",contextId = "login-ordersStatus")
public interface OrdersStatusFeign {
    @RequestMapping(method = RequestMethod.POST,value = "/getOrdersStatusById")
    OrdersStatus getOrdersStatusById(@RequestParam("statusId") String statusId);
    @RequestMapping(method = RequestMethod.POST,value = "/addOrdersStatus")
    Boolean addOrdersStatus(@RequestBody OrdersStatus ordersStatus);
    @RequestMapping(method = RequestMethod.POST,value = "/deleteOrdersStatus")
    Boolean deleteOrdersStatus(@RequestParam("statusId") String statusId);
    @RequestMapping(method = RequestMethod.POST,value = "/updateOrdersStatus")
    Boolean updateOrdersStatus(@RequestBody OrdersStatus ordersStatus);
}
