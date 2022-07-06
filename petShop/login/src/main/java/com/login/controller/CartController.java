package com.login.controller;

import com.login.openFeign.CartFeignService;
import com.login.openFeign.CollectFeignService;
import com.login.openFeign.GoodFeignService;
import com.login.openFeign.StoreFeignService;
import com.soft.entity.Cart;
import com.soft.entity.Collect;
import com.soft.entity.Good;
import com.soft.ov.CartOV;
import com.soft.util.DateUtil;
import com.soft.util.JwtUtils;
import com.soft.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/4 16:33
 **/
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartFeignService cartFeignService;
    @Autowired
    GoodFeignService goodFeignService;
    @Autowired
    StoreFeignService storeFeignService;
    @Autowired
    CollectFeignService collectFeignService;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(method = RequestMethod.POST,value = "/getCartByUserId")
    public Result getCartByUserId(){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        if(!tokenRes.getIsSuccess()){
            res.againLogin("未登录");
            return res;
        }
        String userId = JwtUtils.getUserIdByToken(token);
        List<Cart> cartList = cartFeignService.getCartByUserId(userId);
        List<CartOV> resData = new LinkedList<>();
        if(cartList.size()==0){
            res.fail("购物车空");
            return res;
        }
        res.success("success");
        for(Cart cart:cartList){
            Good good = goodFeignService.getGoodById(cart.getGoodId());
            CartOV temp = new CartOV(good);
            String storeName = storeFeignService.getStoreById(good.getStoreId()).getStoreName();
            temp.setStoreName(storeName);
            temp.setNumber(cart.getNumber());
            if(collectFeignService.getIsCollect(JwtUtils.getUserIdByToken(token),good.getGoodId())){
                temp.setIsCollect(1);
            }else {
                temp.setIsCollect(0);
            }
            resData.add(temp);
        }
        res.setData(resData);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addCart")
    public Result addCart(@RequestParam("goodId") String goodId){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        String userId = JwtUtils.getUserIdByToken(token);
        if(!tokenRes.getIsSuccess()){
            res.againLogin("未登录");
            return res;
        }
        Cart cart = cartFeignService.getCartByUserIdGoodId(userId,goodId);
        if(cart!=null){
            cart.setNumber(cart.getNumber()+1);
            cartFeignService.updateCart(cart);
            res.success("增加成功");
            return res;
        }
        Cart temp = new Cart();
        temp.setGoodId(goodId);
        temp.setUserId(userId);
        temp.setCartId(UUID.randomUUID().toString());
        temp.setAddTime(DateUtil.getLocalDateTimeStr());
        temp.setNumber(1);
        if(cartFeignService.addCart(temp)){
            res.success("添加成功");
        }else {
            res.fail("添加失败");
        }
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteCart")
    public Result deleteCart(@RequestParam("goodId") String goodId){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        if(!tokenRes.getIsSuccess()){
            res.againLogin("未登录");
            return res;
        }
        Cart cart = cartFeignService.getCartByUserIdGoodId(JwtUtils.getUserIdByToken(token),goodId);
        if(cartFeignService.deleteCart(cart.getCartId())){
            res.success("删除成功");
            return res;
        }
        res.fail("删除失败");
        return res;
    }
}
