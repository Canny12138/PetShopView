package com.userManage.controller;

import com.soft.entity.OrderGood;
import com.userManage.mapper.OrderGoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:44
 **/
@RestController
@RequestMapping("/orderGood")
public class OrderGoodController {
    @Autowired
    private OrderGoodMapper orderGoodMapper;
    @RequestMapping(method = RequestMethod.GET,value = "/all")
    public List<OrderGood> all(){ return orderGoodMapper.selectList(null);}

    @RequestMapping(method = RequestMethod.POST,value = "/getOrderGoodById")
    public OrderGood getOrderGoodById(@RequestParam("id") String id){
        OrderGood res;
        res = orderGoodMapper.selectById(id);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addOrderGood")
    public Boolean addOrderGood(@RequestBody OrderGood orderGood) throws Exception{
        OrderGood res;
        res = orderGoodMapper.selectById(orderGood.getId());
        if(res!=null){
            return false;
        }
        orderGoodMapper.insert(orderGood);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteOrderGood")
    public Boolean deleteOrderGood(@RequestParam("id") String id){
        OrderGood res;
        res = orderGoodMapper.selectById(id);
        if(res==null){
            return false;
        }
        orderGoodMapper.deleteById(id);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateOrderGood")
    public Boolean updateOrderGood(@RequestBody OrderGood orderGood){
        OrderGood res;
        res = orderGoodMapper.selectById(orderGood.getId());
        if(res==null){
            return false;
        }
        orderGoodMapper.updateById(orderGood);
        return true;
    }

}
