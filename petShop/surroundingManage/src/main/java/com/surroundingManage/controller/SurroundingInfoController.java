package com.surroundingManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.SurroundingInfo;
import com.surroundingManage.mapper.SurroundingInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 14:43
 **/
@Repository
@RequestMapping("/surroundingInfo")
public class SurroundingInfoController {
    @Autowired
    private SurroundingInfoMapper surroundingInfoMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getInfoById")
    public SurroundingInfo getPetInfoById(@RequestParam("surroundingId") String surroundingId){
        SurroundingInfo params = new SurroundingInfo();
        QueryWrapper<SurroundingInfo> wrapper = new QueryWrapper<>(params);
        wrapper.eq("surrounding_id",surroundingId);
        SurroundingInfo res = surroundingInfoMapper.selectOne(wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addInfo")
    public Boolean addSurroundingInfo(@RequestBody SurroundingInfo surroundingInfo){
        SurroundingInfo res;
        res = surroundingInfoMapper.selectById(surroundingInfo.getInfoId());
        if(res!=null){
            return false;
        }
        surroundingInfoMapper.insert(surroundingInfo);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteInfo")
    public Boolean deleteSurroundingInfo(@RequestParam("infoId") String infoId){
        SurroundingInfo res;
        res = surroundingInfoMapper.selectById(infoId);
        if(res==null){
            return false;
        }
        surroundingInfoMapper.deleteById(infoId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateInfo")
    public Boolean updateSurroundingInfo(@RequestBody SurroundingInfo surroundingInfo){
        SurroundingInfo res;
        res = surroundingInfoMapper.selectById(surroundingInfo.getInfoId());
        if(res==null){
            return false;
        }
        surroundingInfoMapper.updateById(surroundingInfo);
        return true;
    }
}
