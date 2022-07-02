package com.storeService.controller;

import com.soft.entity.*;
import com.soft.ov.GoodInfoOV;
import com.soft.util.Result;
import com.storeService.openFeign.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/30 8:58
 **/
@RestController
@RequestMapping("/goodInfoOV")
public class GoodInfoOVController {
    @Autowired
    GoodFeignService goodFeignService;
    @Autowired
    StoreFeignService storeFeignService;
    @Autowired
    PetFeignService petFeignService;
    @Autowired
    PetTypeFeignService petTypeFeignService;
    @Autowired
    PetInfoFeignService petInfoFeignService;
    @Autowired
    SurroundingFeignService surroundingFeignService;
    @Autowired
    SurroundingTypeFeignService surroundingTypeFeignService;
    @Autowired
    SurroundingInfoFeignService surroundingInfoFeignService;
    @RequestMapping(method = RequestMethod.GET,value = "/getInfo")
    public Result getInfo(@RequestParam("goodId") String goodId){
        Result res = new Result();
        Good good = goodFeignService.getGoodById(goodId);
        GoodInfoOV goodInfoOV = new GoodInfoOV(good);
        goodInfoOV.setStoreName(storeFeignService.getStoreById(good.getStoreId()).getStoreName());
        goodInfoOV.setIsCollect(1);
        if(good.getType()==0){
            Surrounding surrounding = surroundingFeignService.getSurroundingBySurroundingId(good.getThingId());
            SurroundingInfo surroundingInfo = surroundingInfoFeignService.getSurroundingInfoById(surrounding.getSurroundingId());
            SurroundingType surroundingType = surroundingTypeFeignService.getSurroundingTypeByTypeValue(surrounding.getSurroundingType());
            goodInfoOV.setGoodInfo(surroundingInfo.getInfo());
            goodInfoOV.setType(surroundingType.getType());
        }else {
            Pet pet = petFeignService.getPetByPetId(good.getThingId());
            PetInfo petInfo = petInfoFeignService.getPetInfoById(pet.getPetId());
            PetType petType = petTypeFeignService.getPetTypeByTypeValue(pet.getPetType());
            goodInfoOV.setGoodInfo(petInfo.getInfo());
            goodInfoOV.setType(petType.getType());
        }
        res.setData(goodInfoOV);
        return res;
    }
}
