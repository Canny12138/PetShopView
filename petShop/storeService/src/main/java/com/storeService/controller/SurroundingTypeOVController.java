package com.storeService.controller;

import com.soft.entity.SurroundingType;
import com.soft.util.Result;
import com.storeService.openFeign.SurroundingTypeFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 15:57
 **/
@RestController
@RequestMapping("/surroundingTypeOV")
public class SurroundingTypeOVController {
    @Autowired
    SurroundingTypeFeignService surroundingTypeFeignService;
    @RequestMapping(method = RequestMethod.POST,value = "/addSurroundingType")
    public Result addSurroundingType(@RequestParam("typeValue") Integer typeValue, @RequestParam("type") String type){
        Result res = new Result();
        SurroundingType surroundingType = new SurroundingType();
        surroundingType.setTypeId(UUID.randomUUID().toString());
        surroundingType.setTypeValue(typeValue);
        surroundingType.setType(type);
        try {
            surroundingTypeFeignService.addSurroundingType(surroundingType);
            res.success("添加成功");
        }catch (Exception e){
            res.fail("类型已存在");
        }
        return res;
    }
}
