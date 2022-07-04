package com.login.openFeign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft.entity.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="userManage-service",path = "/cart",contextId = "login-cart")
public interface CartFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getCartPageByUserId")
    Page<Cart> page(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("userId") String userId
    );
    @RequestMapping(method = RequestMethod.POST,value = "/getCartByUserId")
    List<Cart> getCartByUserId(@RequestParam("userId") String userId);
    @RequestMapping(method = RequestMethod.POST,value = "/getCartByUserIdGoodId")
    Cart getCartByUserIdGoodId(
            @RequestParam("userId") String userId,
            @RequestParam("goodId") String goodId
    );
    @RequestMapping(method = RequestMethod.POST,value = "getIsCart")
    Boolean getIsCart(@RequestParam("userId") String userId,@RequestParam("goodId") String goodId);
    @RequestMapping(method = RequestMethod.POST,value = "/addCart")
    Boolean addCart(@RequestBody Cart Cart);
    @RequestMapping(method = RequestMethod.POST,value = "/deleteCart")
    Boolean deleteCart(@RequestParam("CartId") String CartId);
    @RequestMapping(method = RequestMethod.POST,value = "/updateCart")
    Boolean updateCart(@RequestBody Cart Cart);
}
