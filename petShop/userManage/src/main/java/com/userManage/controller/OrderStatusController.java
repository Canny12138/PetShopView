package com.userManage.controller;


import com.soft.entity.OrderStatus;
import com.userManage.mapper.OrderStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:40
 **/
@RestController
@RequestMapping("/orderStatus")
public class OrderStatusController {
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @RequestMapping(method = RequestMethod.GET,value = "/all")
    public List<OrderStatus> all(){ return orderStatusMapper.selectList(null);}

    @RequestMapping(method = RequestMethod.POST,value = "/getOrderStatusById")
    public OrderStatus getOrderStatusById(@RequestParam("statusId") String statusId){
        OrderStatus res;
        res = orderStatusMapper.selectById(statusId);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addOrderStatus")
    public Boolean addOrderStatus(@RequestBody OrderStatus orderStatus) throws Exception{
        OrderStatus res;
        res = orderStatusMapper.selectById(orderStatus.getStatusId());
        if(res!=null){
            return false;
        }
        orderStatusMapper.insert(orderStatus);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteOrderStatus")
    public Boolean deleteOrderStatus(@RequestParam("statusId") String statusId){
        OrderStatus res;
        res = orderStatusMapper.selectById(statusId);
        if(res==null){
            return false;
        }
        orderStatusMapper.deleteById(statusId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateOrderStatus")
    public Boolean updateOrderStatus(@RequestBody OrderStatus orderStatus){
        OrderStatus res;
        res = orderStatusMapper.selectById(orderStatus.getStatusId());
        if(res==null){
            return false;
        }
        orderStatusMapper.updateById(orderStatus);
        return true;
    }

}
