package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.Good;
import com.soft.entity.OrdersGood;
import com.userManage.mapper.OrdersGoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:44
 **/
@RestController
@RequestMapping("/ordersGood")
public class OrdersGoodController {
    @Autowired
    private OrdersGoodMapper ordersGoodMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getGoodByOrdersId")
    public List<OrdersGood> getGoodByOrdersId(@RequestParam("orders_id") String orders_id){
        OrdersGood params = new OrdersGood();
        QueryWrapper<OrdersGood> wrapper = new QueryWrapper<>(params);
        wrapper.eq("orders_id",orders_id);
        return ordersGoodMapper.selectList(wrapper);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/getOrderGoodById")
    public OrdersGood getOrderGoodById(@RequestParam("id") String id){
        OrdersGood res;
        res = ordersGoodMapper.selectById(id);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addOrderGood")
    public Boolean addOrderGood(@RequestBody OrdersGood ordersGood) throws Exception{
        OrdersGood res;
        res = ordersGoodMapper.selectById(ordersGood.getId());
        if(res!=null){
            return false;
        }
        ordersGoodMapper.insert(ordersGood);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteOrderGood")
    public Boolean deleteOrderGood(@RequestParam("id") String id){
        OrdersGood res;
        res = ordersGoodMapper.selectById(id);
        if(res==null){
            return false;
        }
        ordersGoodMapper.deleteById(id);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateOrderGood")
    public Boolean updateOrderGood(@RequestBody OrdersGood ordersGood){
        OrdersGood res;
        res = ordersGoodMapper.selectById(ordersGood.getId());
        if(res==null){
            return false;
        }
        ordersGoodMapper.updateById(ordersGood);
        return true;
    }

}
