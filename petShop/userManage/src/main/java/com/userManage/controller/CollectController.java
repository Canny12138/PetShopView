package com.userManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.userManage.mapper.CollectMapper;
import com.soft.entity.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(method = RequestMethod.GET,value = "/test")
    public String test(){
        String res = "hello world";
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getCollectById")
    public Collect getCollectById(@RequestParam("collectId") String collectId){
        Collect params = new Collect();
        QueryWrapper<Collect> wrapper = new QueryWrapper<>(params);
        wrapper.eq("collect_id",collectId);
        Collect res = collectMapper.selectOne(wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addCollect")
    @ResponseBody
    public Boolean addCollect(@RequestBody Collect collect){
        Collect res;
        res = collectMapper.selectById(collect.getCollecId());
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
        res = collectMapper.selectById(collect.getCollecId());
        if(res==null){
            return false;
        }
        collectMapper.updateById(collect);
        return true;
    }
}