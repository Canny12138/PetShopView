package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.Order;
import com.userManage.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:36
 **/
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;
    @RequestMapping(method = RequestMethod.GET,value = "/test")
    public String test(){
        String res = "hello world";
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getOrderById")
    public Order getOrderById(@RequestParam("orderId") String orderId){
        Order params = new Order();
        QueryWrapper<Order> wrapper = new QueryWrapper<>(params);
        wrapper.eq("order_id",orderId);
        Order res = orderMapper.selectOne(wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addOrder")
    @ResponseBody
    public Boolean addOrder(@RequestBody Order order){
        Order res;
        res = orderMapper.selectById(order.getOrderId());
        if(res!=null){
            return false;
        }
        orderMapper.insert(order);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteOrder")
    public Boolean deleteOrder(@RequestParam("orderId") String orderId){
        Order res;
        res = orderMapper.selectById(orderId);
        if(res==null){
            return false;
        }
        orderMapper.deleteById(orderId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateOrder")
    public Boolean updateOrder(@RequestBody Order order){
        Order res;
        res = orderMapper.selectById(order.getOrderId());
        if(res==null){
            return false;
        }
        orderMapper.updateById(order);
        return true;
    }
}
