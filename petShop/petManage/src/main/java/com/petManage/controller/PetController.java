package com.petManage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.Pet;
import com.soft.entity.User;
import com.petManage.mapper.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: petShop
 * @description:
 * @author: liuzhiyu
 * @create: 2022-06-23 15:09
 **/
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetMapper petMapper;
    @RequestMapping(method = RequestMethod.GET,value = "/all")
    public List<Pet> all(){ return petMapper.selectList(null);}
    @RequestMapping(method = RequestMethod.POST,value = "/getPetByPetname")
    public Pet getPetByPetname(@RequestParam("pet_name") String petname){
        Pet param = new Pet();
        QueryWrapper<Pet> wrapper = new QueryWrapper<>(param);
        wrapper.eq("pet_name",petname);
        List<Pet> res;
        res = petMapper.selectList(wrapper);
        if(res.size()==0){
            return null;
        }
        return res.get(0);
    }
    @RequestMapping(method = RequestMethod.POST,value = ("/addPet"))
    public Boolean addPet(@RequestBody Pet pet) throws Exception{
        Pet param = new Pet();
        QueryWrapper<Pet> wrapper = new QueryWrapper<>(param);
        wrapper.eq("pet_id",pet.getPetId());
        List<Pet> res;
        res = petMapper.selectList(wrapper);
        if(res.size()!=0){
            return false;
        }
        petMapper.insert(pet);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = ("/deletePet"))
    public Boolean deletePet(@RequestParam("petId") String petId){
        Pet param = new Pet();
        QueryWrapper<Pet> wrapper = new QueryWrapper<>(param);
        wrapper.eq("pet_id",petId);
        List<Pet> res;
        res = petMapper.selectList(wrapper);
        if(res.size()==0){
            return false;
        }
        petMapper.deleteById(petId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = ("/updatePet"))
    public Boolean updatePet(@RequestBody Pet pet){
        Pet param = new Pet();
        QueryWrapper<Pet> wrapper = new QueryWrapper<>(param);
        wrapper.eq("pet_id",pet.getPetId());
        List<Pet> res;
        res = petMapper.selectList(wrapper);
        if(res.size()==0){
            return false;
        }
        petMapper.updateById(pet);
        return true;
    }

}
