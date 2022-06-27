package com.storeService.openFeign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft.entity.Good;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 10:34
 **/
@FeignClient(name="goodManage-service",path = "/good")
public interface GoodFeignService {
    @RequestMapping(method = RequestMethod.GET,value = "/page")
    Page<Good> page(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("goodName") String goodName
    );

    @RequestMapping(method = RequestMethod.GET,value = "/pageByStoreId")
    Page<Good> pageByStoreId(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("storeId") String storeId
    );

    @RequestMapping(method = RequestMethod.POST,value = "/addGood")
    Boolean addGood(@RequestBody Good good);

    @RequestMapping(method = RequestMethod.POST,value = "/deleteGood")
    Boolean deleteGood(@RequestParam("goodId") String goodId);
}
