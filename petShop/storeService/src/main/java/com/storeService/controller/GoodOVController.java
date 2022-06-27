package com.storeService.controller;

import com.soft.entity.*;
import com.soft.ov.GoodOV;
import com.soft.util.Result;
import com.storeService.openFeign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 10:32
 **/
@RestController
@RequestMapping("/goodOV")
public class GoodOVController {
    @Autowired
    GoodFeignService goodFeignService;
    @Autowired
    PetFeignService petFeignService;
    @Autowired
    PetTypeFeignService petTypeFeignService;
    @Autowired
    SurroundingFeignService surroundingFeignService;
    @Autowired
    SurroundingTypeFeignService surroundingTypeFeignService;
    @RequestMapping(method = RequestMethod.GET,value = "/getGoodOVByPage")
    public Result getGoodOVByPage(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("goodName") String goodName
            ){
        Result res = new Result();
        List<Good> records= goodFeignService.page(pageNum,pageSize,goodName);
        List<GoodOV> resData = new LinkedList<>();
        if(records.size()==0){
            res.fail("未找到商品");
        }else {
            res.success("success");
        }
        for(Good good:records){
            GoodOV temp = new GoodOV(good);
            if(good.getType()==0){
                Surrounding surrounding = surroundingFeignService.getSurroundingBySurroundingId(good.getThingId());
                temp.setThing(surrounding);
                SurroundingType surroundingType = surroundingTypeFeignService.getSurroundingTypeByTypeValue(surrounding.getSurroundType());
                temp.setType(surroundingType);

            }else {
                Pet pet = petFeignService.getPetByPetId(good.getThingId());
                temp.setThing(pet);
                PetType petType = petTypeFeignService.getPetTypeByTypeValue(good.getType());
                temp.setType(petType);
            }
            resData.add(temp);
        }
        return res;
    }

}
