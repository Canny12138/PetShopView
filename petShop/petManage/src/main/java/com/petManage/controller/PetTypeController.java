package com.petManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.petManage.mapper.PetTypeMapper;
import com.soft.entity.PetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 9:23
 **/
@RestController
@RequestMapping("/petType")
public class PetTypeController {
    @Autowired
    private PetTypeMapper petTypeMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getPetType")
    public PetType getPetTypeByTypeValue(@RequestParam("typeValue") Integer typeValue){
        PetType params = new PetType();
        QueryWrapper<PetType> wrapper = new QueryWrapper<>(params);
        wrapper.eq("type_value",typeValue);
        PetType res = petTypeMapper.selectOne(wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addPetType")
    public Boolean addPetType(@RequestBody PetType petType){
        PetType res;
        res = petTypeMapper.selectById(petType.getTypeId());
        if(res!=null){
            return false;
        }
        petTypeMapper.insert(petType);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deletePetType")
    public Boolean deletePetType(@RequestParam("typeId") String typeId){
        PetType res;
        res = petTypeMapper.selectById(typeId);
        if(res==null){
            return false;
        }
        petTypeMapper.deleteById(typeId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updatePetType")
    public Boolean updatePetType(@RequestBody PetType petType){
        PetType res;
        res = petTypeMapper.selectById(petType.getTypeId());
        if(res==null){
            return false;
        }
        petTypeMapper.updateById(petType);
        return true;
    }
}
