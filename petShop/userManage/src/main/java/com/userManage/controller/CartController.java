package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft.entity.Cart;
import com.userManage.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/4 15:49
 **/
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartMapper cartMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getCartPageByUserId")
    public Page<Cart> page(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("userId") String userId
    ){
        Page<Cart> page = new Page<>(pageNum,pageSize);
        Cart params = new Cart();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId);
        return cartMapper.selectPage(page,wrapper);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getCartByUserId")
    public List<Cart> getCartByUserId(@RequestParam("userId") String userId){
        Cart params = new Cart();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId);
        return cartMapper.selectList(wrapper);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getCartByUserIdGoodId")
    public Cart getCartByUserIdGoodId(
            @RequestParam("userId") String userId,
            @RequestParam("goodId") String goodId
    ){
        Cart params = new Cart();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId).eq("good_id",goodId);
        return cartMapper.selectOne(wrapper);
    }
    @RequestMapping(method = RequestMethod.POST,value = "getIsCart")
    public Boolean getIsCart(@RequestParam("userId") String userId,@RequestParam("goodId") String goodId){
        Cart params = new Cart();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId).eq("good_id",goodId);
        return cartMapper.selectOne(wrapper) != null;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addCart")
    public Boolean addCart(@RequestBody Cart Cart){
        Cart res;
        res = cartMapper.selectById(Cart.getCartId());
        if(res!=null){
            return false;
        }
        cartMapper.insert(Cart);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteCart")
    public Boolean deleteCart(@RequestParam("CartId") String CartId){
        Cart res;
        res = cartMapper.selectById(CartId);
        if(res==null){
            return false;
        }
        cartMapper.deleteById(CartId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateCart")
    public Boolean updateCart(@RequestBody Cart Cart){
        Cart res;
        res = cartMapper.selectById(Cart.getCartId());
        if(res==null){
            return false;
        }
        cartMapper.updateById(Cart);
        return true;
    }
}
