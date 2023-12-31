package com.login.openFeign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft.entity.Good;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 10:34
 **/
@FeignClient(name="goodManage-service",path = "/good",contextId = "login-good")
public interface GoodFeignService {
    @RequestMapping(method = RequestMethod.GET,value = "/page")
    Page<Good> page(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("goodName") String goodName,
            @RequestParam("type") String type
    );
    @RequestMapping(method = RequestMethod.GET,value = "/getGoodById")
    Good getGoodById(@RequestParam("goodId") String goodId);
    @RequestMapping(method = RequestMethod.GET,value = "/pageByStoreId")
    List<Good> pageByStoreId(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("storeId") String storeId
    );

    @RequestMapping(method = RequestMethod.POST,value = "/addGood")
    Boolean addGood(@RequestBody Good good);

    @RequestMapping(method = RequestMethod.POST,value = "/deleteGood")
    Boolean deleteGood(@RequestParam("goodId") String goodId);
    @RequestMapping(method = RequestMethod.POST,value = "/updateGood")
    Boolean updateGood(@RequestBody Good good);
}
