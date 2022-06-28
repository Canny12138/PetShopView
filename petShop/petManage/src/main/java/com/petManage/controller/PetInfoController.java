package com.petManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.petManage.mapper.PetInfoMapper;
import com.soft.entity.PetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 15:14
 **/
@Repository
@RequestMapping("/petInfo")
public class PetInfoController {
    @Autowired
    private PetInfoMapper petInfoMapper;
    @RequestMapping(method = RequestMethod.POST,value = "/getInfoById")
    public PetInfo getPetInfoById(@RequestParam("petId") String petId){
        PetInfo params = new PetInfo();
        QueryWrapper<PetInfo> wrapper = new QueryWrapper<>(params);
        wrapper.eq("pet_id",petId);
        PetInfo res = petInfoMapper.selectOne(wrapper);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addInfo")
    @ResponseBody
    public Boolean addPetInfo(@RequestBody PetInfo petInfo){
        PetInfo res;
        res = petInfoMapper.selectById(petInfo.getInfoId());
        if(res!=null){
            return false;
        }
        petInfoMapper.insert(petInfo);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteInfo")
    public Boolean deletePetInfo(@RequestParam("infoId") String infoId){
        PetInfo res;
        res = petInfoMapper.selectById(infoId);
        if(res==null){
            return false;
        }
        petInfoMapper.deleteById(infoId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updateInfo")
    public Boolean updatePetInfo(@RequestBody PetInfo petInfo){
        PetInfo res;
        res = petInfoMapper.selectById(petInfo.getInfoId());
        if(res==null){
            return false;
        }
        petInfoMapper.updateById(petInfo);
        return true;
    }
}
