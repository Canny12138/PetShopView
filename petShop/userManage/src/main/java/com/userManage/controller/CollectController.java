package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userManage.mapper.CollectMapper;
import com.soft.entity.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:17
 **/
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectMapper collectMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getCollectPageByUserId")
    public Page<Collect> page(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("userId") String userId
    ){
        Page<Collect> page = new Page<>(pageNum,pageSize);
        Collect params = new Collect();
        QueryWrapper<Collect> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId);
        return collectMapper.selectPage(page,wrapper);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getCollectByUserIdGoodId")
    public Collect getCollectByUserIdGoodId(
            @RequestParam("userId") String userId,
            @RequestParam("goodId") String goodId
    ){
        Collect params = new Collect();
        QueryWrapper<Collect> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId).eq("good_id",goodId);
        return collectMapper.selectOne(wrapper);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getCollectByUserId")
    public List<Collect> getCollectByUserId(@RequestParam("userId") String userId){
        Collect params = new Collect();
        QueryWrapper<Collect> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId);
        return collectMapper.selectList(wrapper);
    }
    @RequestMapping(method = RequestMethod.POST,value = "getIsCollect")
    public Boolean getIsCollect(@RequestParam("userId") String userId,@RequestParam("goodId") String goodId){
        Collect params = new Collect();
        QueryWrapper<Collect> wrapper = new QueryWrapper<>(params);
        wrapper.eq("user_id",userId).eq("good_id",goodId);
        return collectMapper.selectOne(wrapper) != null;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addCollect")
    public Boolean addCollect(@RequestBody Collect collect){
        Collect res;
        res = collectMapper.selectById(collect.getCollectId());
        if(res!=null){
            return false;
        }
        collectMapper.insert(collect);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteCollect")
    public Boolean deleteCollect(@RequestParam("collectId") String collectId){
        Collect res;
        res = collectMapper.selectById(collectId);
        if(res==null){
            return false;
        }
        collectMapper.deleteById(collectId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateCollect")
    public Boolean updateCollect(@RequestBody Collect collect){
        Collect res;
        res = collectMapper.selectById(collect.getCollectId());
        if(res==null){
            return false;
        }
        collectMapper.updateById(collect);
        return true;
    }
}