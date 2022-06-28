package com.surroundingManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.SurroundingType;
import com.surroundingManage.mapper.SurroundingTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 9:24
 **/
@RestController
@RequestMapping("/surroundingType")
public class SurroundingTypeController {
    @Autowired
    private SurroundingTypeMapper surroundingTypeMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getSurroundingType")
    public SurroundingType getSurroundingTypeByTypeValue(@RequestParam("typeValue") Integer typeValue){
        SurroundingType params = new SurroundingType();
        QueryWrapper<SurroundingType> wrapper = new QueryWrapper<>(params);
        wrapper.eq("type_value",typeValue);
        SurroundingType res = surroundingTypeMapper.selectOne(wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addSurroundingType")
    public Boolean addSurroundingType(@RequestBody SurroundingType surroundingType){
        SurroundingType res = surroundingTypeMapper.selectById(surroundingType.getTypeId());
        if(res!=null){
            return false;
        }
        surroundingTypeMapper.insert(surroundingType);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteSurroundingType")
    public Boolean deleteSurroundingType(@RequestParam("typeId") String typeId){
        SurroundingType res;
        res = surroundingTypeMapper.selectById(typeId);
        if(res==null){
            return false;
        }
        surroundingTypeMapper.deleteById(typeId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateSurroundingType")
    public Boolean updatePetType(@RequestBody SurroundingType surroundingType){
        SurroundingType res;
        res = surroundingTypeMapper.selectById(surroundingType.getTypeId());
        if(res==null){
            return false;
        }
        surroundingTypeMapper.updateById(surroundingType);
        return true;
    }
}
