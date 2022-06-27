package com.surroundingManage.controller;

import com.soft.entity.Surrounding;
import com.surroundingManage.mapper.SurroundingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 8:22
 **/
@RestController
@RequestMapping("/surrounding")
public class SurroundingController {
    @Autowired
    private SurroundingMapper surroundingMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getSurroundingById")
    public Surrounding getSurroundingBySurroundingId(@RequestParam("surroundingId") String SurroundingId){
        Surrounding res;
        res = surroundingMapper.selectById(SurroundingId);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = ("/addSurrounding"))
    public Boolean addSurrounding(@RequestBody Surrounding surrounding){
        Surrounding res;
        res = surroundingMapper.selectById(surrounding.getSurroundingId());
        if(res!=null){
            return false;
        }
        surroundingMapper.insert(surrounding);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = ("/deleteSurrounding"))
    public Boolean deleteSurrounding(@RequestParam("surroundingId") String surroundId){
        Surrounding res;
        res = surroundingMapper.selectById(surroundId);
        if(res==null){
            return false;
        }
        surroundingMapper.deleteById(surroundId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = ("/updateSurrounding"))
    public Boolean updateSurrounding(@RequestBody Surrounding surrounding){
        Surrounding res;
        res = surroundingMapper.selectById(surrounding.getSurroundingId());
        if(res==null){
            return false;
        }
        surroundingMapper.updateById(surrounding);
        return true;
    }
}
