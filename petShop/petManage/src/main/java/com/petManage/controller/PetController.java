package com.petManage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft.entity.Pet;
import com.petManage.mapper.PetMapper;
import com.soft.util.Result;
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

    @RequestMapping(method = RequestMethod.POST,value = "/getPetByPetId")
    public Pet getPetByPetId(@RequestParam("petId") String petId){
        Pet res;
        res = petMapper.selectById(petId);
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/addPet")
    public Boolean addPet(@RequestBody Pet pet) throws Exception{
        Pet res;
        res = petMapper.selectById(pet.getPetId());
        if(res!=null){
            return false;
        }
        petMapper.insert(pet);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deletePet")
    public Boolean deletePet(@RequestParam("petId") String petId){
        Pet res;
        res = petMapper.selectById(petId);
        if(res==null){
            return false;
        }
        petMapper.deleteById(petId);
        return true;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/updatePet")
    public Boolean updatePet(@RequestBody Pet pet){
        Pet res;
        res = petMapper.selectById(pet.getPetId());
        if(res==null){
            return false;
        }
        petMapper.updateById(pet);
        return true;
    }

}
