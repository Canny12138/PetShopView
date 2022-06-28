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
import java.util.UUID;

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
    PetInfoFeignService petInfoFeignService;
    @Autowired
    SurroundingFeignService surroundingFeignService;
    @Autowired
    SurroundingTypeFeignService surroundingTypeFeignService;
    @Autowired
    SurroundingInfoFeignService surroundingInfoFeignService;
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
            for(Good good:records){
                GoodOV temp = new GoodOV(good);
                if(good.getType()==0){
                    Surrounding surrounding = surroundingFeignService.getSurroundingBySurroundingId(good.getThingId());
                    temp.setThing(surrounding);
                    SurroundingType surroundingType = surroundingTypeFeignService.getSurroundingTypeByTypeValue(surrounding.getSurroundingType());
                    temp.setType(surroundingType);
                    SurroundingInfo surroundingInfo = surroundingInfoFeignService.getSurroundingInfoById(good.getThingId());
                    temp.setThingInfo(surroundingInfo);
                }else {
                    Pet pet = petFeignService.getPetByPetId(good.getThingId());
                    temp.setThing(pet);
                    PetType petType = petTypeFeignService.getPetTypeByTypeValue(pet.getPetType());
                    temp.setType(petType);
                    PetInfo petInfo = petInfoFeignService.getPetInfoById(good.getThingId());
                    temp.setThingInfo(petInfo);
                }
                resData.add(temp);
            }
        }
        res.setData(resData);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addGoodOV")
    public Result addGood(
            @RequestParam("goodName") String goodName,
            @RequestParam("goodType") Integer goodType,
            @RequestParam("price") Double price,
            @RequestParam("img") String img,
            @RequestParam("stock") Integer stock,
            @RequestParam("thingType") Integer thingType,
            @RequestParam("thingInfo") String thingInfo,
            @RequestParam("storeId") String storeId
    ){
        Result result = new Result();
        Good good = new Good();
        good.setGoodId(UUID.randomUUID().toString());
        good.setGoodName(goodName);
        good.setType(goodType);
        good.setPrice(price);
        good.setStock(stock);
        good.setImg(img);
        good.setUseful(1);
        good.setStoreId(storeId);
        try {
            if(good.getType()==0){
                Surrounding surrounding = new Surrounding();
                surrounding.setSurroundingId(UUID.randomUUID().toString());
                surrounding.setSurroundingName(good.getGoodName());
                surrounding.setSurroundingType(thingType);
                SurroundingInfo surroundingInfo = new SurroundingInfo();
                surroundingInfo.setInfoId(UUID.randomUUID().toString());
                surroundingInfo.setInfo(thingInfo);
                surroundingInfo.setSurroundingId(surrounding.getSurroundingId());
                good.setThingId(surrounding.getSurroundingId());
                surroundingFeignService.addSurrounding(surrounding);
                surroundingInfoFeignService.addSurroundingInfo(surroundingInfo);
                goodFeignService.addGood(good);
            }else {
                Pet pet = new Pet();
                pet.setPetId(UUID.randomUUID().toString());
                pet.setPetName(good.getGoodName());
                pet.setPetType(thingType);
                PetInfo petInfo = new PetInfo();
                petInfo.setInfoId(UUID.randomUUID().toString());
                petInfo.setInfo(thingInfo);
                petInfo.setPetId(pet.getPetId());
                good.setThingId(pet.getPetId());
                petFeignService.addPet(pet);
                petInfoFeignService.addPetInfo(petInfo);
                goodFeignService.addGood(good);
            }
            result.success("添加成功");
        }catch (Exception e){
            result.fail("添加失败");
            result.fail(e.toString());
        }
        return result;
    }

}
