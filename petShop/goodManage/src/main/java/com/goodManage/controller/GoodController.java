package com.goodManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodManage.mapper.GoodMapper;
import com.soft.entity.Good;
import com.soft.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 8:45
 **/
@RestController
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private GoodMapper goodMapper;
    @RequestMapping(method = RequestMethod.GET,value = "/page")
    public Page<Good> page(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("goodName") String goodName,
            @RequestParam("type") Integer type,
            @RequestParam("storeName") String storeName
    ){
        Page<Good> page = new Page<>(pageNum,pageSize);
        Good params = new Good();
        QueryWrapper<Good> wrapper = new QueryWrapper<>(params);
        if(goodName!=null&&!"".equals(goodName)){
            wrapper.like("good_name",goodName);
        }
        if(storeName!=null&&!"".equals(storeName)){
            wrapper.like("store_name",storeName);
        }
        if(type==1||type==0){
            wrapper.eq("type",type);
        }
        Page<Good> res = goodMapper.selectPage(page,wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.GET,value = "/getGoodById")
    public Good getGoodById(@RequestParam("goodId") String goodId){
        return goodMapper.selectById(goodId);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/pageByStoreId")
    public List<Good> pageByStoreId(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("storeId") String storeId,
            @RequestParam("goodName") String goodName
    ){
        Page<Good> page = new Page<Good>(pageNum,pageSize);
        Good params = new Good();
        QueryWrapper<Good> wrapper = new QueryWrapper<Good>(params);
//        wrapper.eq("store_id",storeId);
        Page<Good> res = goodMapper.selectPage(page,wrapper);
        return res.getRecords();
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addGood")
    public Boolean addGood(@RequestBody Good good){
        Good res;
        res = goodMapper.selectById(good.getGoodId());
        if(res!=null){
            return false;
        }
        goodMapper.insert(good);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteGood")
    public Boolean deleteGood(@RequestParam("goodId") String goodId){
        Good res;
        res = goodMapper.selectById(goodId);
        if(res==null){
            return false;
        }
        goodMapper.deleteById(goodId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateGood")
    public Boolean updateGood(@RequestBody Good good){
        Good res;
        res = goodMapper.selectById(good.getGoodId());
        if(res==null){
            return false;
        }
        goodMapper.updateById(good);
        return true;
    }
}
