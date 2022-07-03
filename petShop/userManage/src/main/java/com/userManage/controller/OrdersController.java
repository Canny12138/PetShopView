package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.Orders;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.userManage.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:36
 **/
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersMapper ordersMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getOrdersByUserId")
    public List<Orders> getOrdersByUserId(@RequestParam("userId") String userId){
        Orders params = new Orders();
        List<Orders> res;
        QueryWrapper<Orders> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId);
        res = ordersMapper.selectList(wrapper);
        return res;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/getOrdersById")
    public Orders getOrdersById(@RequestParam("ordersId") String ordersId){
        Orders res;
        res  = ordersMapper.selectById(ordersId);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addOrders")
    public Boolean addOrders(@RequestBody Orders orders){
        Orders res;
        res = ordersMapper.selectById(orders.getOrdersId());
        if(res!=null){
            return false;
        }
        ordersMapper.insert(orders);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteOrders")
    public Boolean deleteOrders(@RequestParam("ordersId") String ordersId){
        Orders res;
        res = ordersMapper.selectById(ordersId);
        if(res==null){
            return false;
        }
        ordersMapper.deleteById(ordersId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateOrders")
    public Boolean updateOrders(@RequestBody Orders orders){
        Orders res;
        res = ordersMapper.selectById(orders.getOrdersId());
        if(res==null){
            return false;
        }
        ordersMapper.updateById(orders);
        return true;
    }
}
