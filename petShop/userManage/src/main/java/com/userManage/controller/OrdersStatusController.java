package com.userManage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.OrdersStatus;
import com.userManage.mapper.OrdersStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:40
 **/
@RestController
@RequestMapping("/ordersStatus")
public class OrdersStatusController {
    @Autowired
    private OrdersStatusMapper ordersStatusMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getOrdersStatusById")
    public OrdersStatus getOrdersStatusById(@RequestParam("statusId") String statusId){
        OrdersStatus res;
        res = ordersStatusMapper.selectById(statusId);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getOrdersStatusByValue")
    public OrdersStatus getOrdersStatusByValue(@RequestParam("value") Integer value){
        OrdersStatus params = new OrdersStatus();
        QueryWrapper<OrdersStatus> wrapper = new QueryWrapper<>(params);
        wrapper.eq("status_value",value);
        return ordersStatusMapper.selectOne(wrapper);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addOrdersStatus")
    public Boolean addOrdersStatus(@RequestBody OrdersStatus ordersStatus) {
        OrdersStatus res;
        System.out.println(ordersStatus.getStatusId());
        System.out.println(ordersStatus.getStatus());
        System.out.println(ordersStatus.getStatusValue());
        res = ordersStatusMapper.selectById(ordersStatus.getStatusId());
        if(res!=null){
            return false;
        }
        ordersStatusMapper.insert(ordersStatus);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteOrdersStatus")
    public Boolean deleteOrdersStatus(@RequestParam("statusId") String statusId){
        OrdersStatus res;
        res = ordersStatusMapper.selectById(statusId);
        if(res==null){
            return false;
        }
        ordersStatusMapper.deleteById(statusId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateOrdersStatus")
    public Boolean updateOrdersStatus(@RequestBody OrdersStatus ordersStatus){
        OrdersStatus res;
        res = ordersStatusMapper.selectById(ordersStatus.getStatusId());
        if(res==null){
            return false;
        }
        ordersStatusMapper.updateById(ordersStatus);
        return true;
    }

}
