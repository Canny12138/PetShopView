package com.storeService.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.soft.entity.PetType;
import com.soft.util.Result;
import com.storeService.openFeign.PetTypeFeignService;
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
@RequestMapping("/petTypeOV")
public class PetTypeOVController {
    @Autowired
    PetTypeFeignService petTypeFeignService;
    @RequestMapping(method = RequestMethod.POST,value = "/addPetType")
    public Result addPetType(@RequestParam("typeValue") Integer typeValue, @RequestParam("type") String type){
        Result res = new Result();
        PetType petType = new PetType();
        petType.setTypeId(UUID.randomUUID().toString());
        petType.setTypeValue(typeValue);
        petType.setType(type);
        if(petTypeFeignService.addPetType(petType)){
            res.success("添加成功");
        }else {
            res.fail("类型已存在");
        }
        return res;
    }

}
