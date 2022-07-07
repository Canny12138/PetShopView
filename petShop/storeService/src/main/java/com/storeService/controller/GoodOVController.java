package com.storeService.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft.entity.*;
import com.soft.ov.GoodOV;
import com.soft.util.JwtUtils;
import com.soft.util.Result;
import com.storeService.openFeign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
    @Autowired
    StoreFeignService storeFeignService;
    @Autowired
    CollectFeignService collectFeignService;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(method = RequestMethod.GET,value = "/getGoodOVByPage")
    public Result getGoodOVByPage(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("goodName") String goodName,
            @RequestParam("type") String type,
            @RequestParam("storeId") String storeId
            ){
        Result res = new Result();
        String token = request.getHeader("token");
        Page<Good> page= goodFeignService.page(pageNum,pageSize,goodName,type,storeId);
        List<Good> records = page.getRecords();
        List<GoodOV> resData = new LinkedList<>();
        if(records.size()==0){
            res.fail("未找到商品");
        }else {
            res.success("success");
            for(Good good:records){
                GoodOV temp = new GoodOV(good);
                String store = storeFeignService.getStoreById(good.getStoreId()).getStoreName();
                temp.setStoreName(store);
                Result tokenRes = JwtUtils.validateToken(token);
                if(tokenRes.getIsSuccess()){
                    if(collectFeignService.getIsCollect(JwtUtils.getUserIdByToken(token),good.getGoodId())){
                        temp.setIsCollect(1);
                    }else {
                        temp.setIsCollect(0);
                    }
                }else {
                    temp.setIsCollect(0);
                }
                resData.add(temp);
            }
        }
        res.setData(resData);
        res.setEtc(page.getPages());
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
            }
            goodFeignService.addGood(good);
            result.success("添加成功");
        }catch (Exception e){
            result.fail("添加失败");
            result.fail(e.toString());
        }
        return result;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getRecommend")
    public Result getRecommend(@RequestParam("number") Integer number){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        if(!tokenRes.getIsSuccess()){
            res.fail("暂无推荐");
            return res;
        }else {
            String userId = JwtUtils.getUserIdByToken(token);
            List<Collect> collectList = collectFeignService.getCollectByNoUserId(userId);
            List<GoodOV> resData = new LinkedList<>();
            for(Collect collect:collectList){
                Good good = goodFeignService.getGoodById(collect.getGoodId());
                GoodOV temp = new GoodOV(good);
                String store = storeFeignService.getStoreById(good.getStoreId()).getStoreName();
                temp.setStoreName(store);
                temp.setIsCollect(0);
                resData.add(temp);
                if(number<=resData.size()){
                    break;
                }
            }
            res.setData(resData);
            res.setEtc(resData.size());
            res.success("获取成功");
        }
        return res;
    }
}
