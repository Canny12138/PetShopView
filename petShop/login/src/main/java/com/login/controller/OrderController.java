package com.login.controller;

import com.login.openFeign.*;
import com.soft.entity.Cart;
import com.soft.entity.Good;
import com.soft.entity.Orders;
import com.soft.entity.OrdersGood;
import com.soft.util.DateUtil;
import com.soft.util.JwtUtils;
import com.soft.util.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/5 9:39
 **/
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CartFeignService cartFeignService;
    @Autowired
    private OrdersFeign ordersFeign;
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private GoodFeignService goodFeignService;
    @Autowired
    private OrdersGoodFeign ordersGoodFeign;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(method = RequestMethod.POST,value = "/addOrderByCart")
    public Result addOrderByCart(@RequestParam("addressId") String addressId){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        if(!tokenRes.getIsSuccess()||userFeignService.getUserByUserId(JwtUtils.getUserIdByToken(token))==null){
            res.againLogin("未登录");
            return res;
        }
        List<Cart> cartList = cartFeignService.getCartByUserId(JwtUtils.getUserIdByToken(token));
        Orders orders = new Orders();
        orders.setOrdersId(UUID.randomUUID().toString());
        orders.setOrdersStatus(0);
        orders.setCreateTime(DateUtil.getLocalDateTimeStr());
        orders.setUserId(JwtUtils.getUserIdByToken(token));
        orders.setAddressId(addressId);
        orders.setPrice(0.0);
        for(Cart cart:cartList){
            Good good = goodFeignService.getGoodById(cart.getGoodId());
            if(good.getStock()<=0){
                continue;
            }
            good.setStock(good.getStock()-1);
            orders.setPrice(orders.getPrice()+good.getPrice());
            OrdersGood ordersGood = new OrdersGood();
            ordersGood.setOrdersId(orders.getOrdersId());
            ordersGood.setGoodId(cart.getGoodId());
            ordersGood.setId(UUID.randomUUID().toString());
            if(!ordersGoodFeign.addOrdersGood(ordersGood)){
                res.fail("添加失败");
                return res;
            }
            if(!goodFeignService.updateGood(good)){
                res.fail("添加失败");
                return res;
            }
        }
        if(!ordersFeign.addOrders(orders)){
            res.fail("添加失败");
            return res;
        }

        res.success("下单成功");
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addOrderByGood")
    public Result addOrderByGood(@RequestParam("goodId") String goodId,@RequestParam("addressId") String addressId){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        if(!tokenRes.getIsSuccess()||userFeignService.getUserByUserId(JwtUtils.getUserIdByToken(token))==null){
            res.againLogin("未登录");
            return res;
        }
        Good good = goodFeignService.getGoodById(goodId);
        good.setStock(good.getStock()-1);
        if(good.getStock()<=0){
            res.fail("库存不足");
        }
        Orders orders = new Orders();
        orders.setOrdersId(UUID.randomUUID().toString());
        orders.setOrdersStatus(0);
        orders.setCreateTime(DateUtil.getLocalDateTimeStr());
        orders.setUserId(JwtUtils.getUserIdByToken(token));
        orders.setAddressId(addressId);
        orders.setPrice(good.getPrice());
        OrdersGood ordersGood = new OrdersGood();
        ordersGood.setOrdersId(orders.getOrdersId());
        ordersGood.setGoodId(goodId);
        ordersGood.setId(UUID.randomUUID().toString());
        if(!goodFeignService.updateGood(good)){
            res.fail("添加失败");
            return res;
        }
        if(!ordersGoodFeign.addOrdersGood(ordersGood)){
            res.fail("添加失败");
            return res;
        }
        if(!ordersFeign.addOrders(orders)){
            res.fail("添加失败");
            return res;
        }
        res.success("下单成功");
        return res;
    }
}
