package com.login.controller;

import com.login.openFeign.*;
import com.soft.entity.*;
import com.soft.ov.OrderOV;
import com.soft.util.DateUtil;
import com.soft.util.JwtUtils;
import com.soft.util.Result;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private AddressFeignService addressFeignService;
    @Autowired
    private OrdersStatusFeign ordersStatusFeign;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(method = RequestMethod.POST,value = "/addOrderByCart")
    public Result addOrderByCart(@RequestParam("addressId") String addressId){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        String userId = JwtUtils.getUserIdByToken(token);
        User user = userFeignService.getUserByUserId(userId);
        if(!tokenRes.getIsSuccess()||user==null){
            res.againLogin("未登录");
            return res;
        }
        List<Cart> cartList = cartFeignService.getCartByUserId(JwtUtils.getUserIdByToken(token));
        List<Good> goodList = new LinkedList<>();
        String ordersId = UUID.randomUUID().toString();
        for(Cart cart:cartList){
            goodList.add(goodFeignService.getGoodById(cart.getGoodId()));
        }
        try {
            Orders orders = new Orders();
            orders.setOrdersId(ordersId);
            orders.setOrdersStatus(0);
            orders.setCreateTime(DateUtil.getLocalDateTimeStr());
            orders.setUserId(userId);
            orders.setAddressId(addressId);
            orders.setPrice(0.0);
            for(Cart cart:cartList){
                Good good = goodFeignService.getGoodById(cart.getGoodId());
                if(good.getStock()<=0){
                    throw new Exception();
                }
                good.setStock(good.getStock()-cart.getNumber());
                orders.setPrice(orders.getPrice()+good.getPrice()*cart.getNumber());
                OrdersGood ordersGood = new OrdersGood();
                ordersGood.setOrdersId(orders.getOrdersId());
                ordersGood.setGoodId(cart.getGoodId());
                ordersGood.setId(UUID.randomUUID().toString());
                ordersGood.setNumber(cart.getNumber());
                if(!ordersGoodFeign.addOrdersGood(ordersGood)){
                    throw new Exception();
                }
                if(!goodFeignService.updateGood(good)){
                    throw new Exception();
                }
                cartFeignService.deleteCart(cart.getCartId());
            }
            if(!ordersFeign.addOrders(orders)){
                throw new Exception();
            }
        }catch (Exception e){
            for(Good good:goodList){
                goodFeignService.updateGood(good);
            }
            ordersFeign.deleteOrders(ordersId);
            ordersGoodFeign.deleteOrdersGoodByOrdersId(ordersId);
            res.fail("下单失败");
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
        String ordersId = UUID.randomUUID().toString();
        try {
            Good temp = goodFeignService.getGoodById(goodId);
            if(good.getStock()<=0){
                throw new Exception();
            }
            temp.setStock(temp.getStock()-1);
            Orders orders = new Orders();
            orders.setOrdersId(ordersId);
            orders.setOrdersStatus(0);
            orders.setCreateTime(DateUtil.getLocalDateTimeStr());
            orders.setUserId(JwtUtils.getUserIdByToken(token));
            orders.setAddressId(addressId);
            orders.setPrice(temp.getPrice());
            OrdersGood ordersGood = new OrdersGood();
            ordersGood.setOrdersId(orders.getOrdersId());
            ordersGood.setGoodId(goodId);
            ordersGood.setId(UUID.randomUUID().toString());
            if(!goodFeignService.updateGood(temp)){
                throw new Exception();
            }
            if(!ordersGoodFeign.addOrdersGood(ordersGood)){
                throw new Exception();
            }
            if(!ordersFeign.addOrders(orders)){
                throw new Exception();
            }

        }catch (Exception e){
            goodFeignService.updateGood(good);
            ordersFeign.deleteOrders(ordersId);
            ordersGoodFeign.deleteOrdersGoodByOrdersId(ordersId);
            res.fail("下单失败");
            return res;
        }
        res.success("下单成功");
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getOrdersByUserId")
    public Result getOrderByUserId(){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        String userId = JwtUtils.getUserIdByToken(token);
        User user = userFeignService.getUserByUserId(userId);
        if(!tokenRes.getIsSuccess()||user==null){
            res.againLogin("未登录");
            return res;
        }
        try {
            List<Orders> ordersList = ordersFeign.getOrdersByUserId(userId);
            List<OrderOV> resData = new LinkedList<>();
            for(Orders orders:ordersList){
                UserAddress userAddress = addressFeignService.getAddressByAddressId(orders.getAddressId());
                OrdersStatus ordersStatus = ordersStatusFeign.getOrdersStatusByValue(orders.getOrdersStatus());
                OrderOV orderOV = new OrderOV(orders,userAddress,ordersStatus);
                resData.add(orderOV);
            }
            res.setData(resData);
            res.success("获取成功");
        }catch (Exception e){
            res.fail("获取失败");
        }
        return res;
    }
}
