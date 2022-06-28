package com.storeService.openFeign;

import com.soft.entity.Pet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="petManage-service",path = "/pet")
public interface PetFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getPetByPetId")
    Pet getPetByPetId(@RequestParam("petId") String petId);

    @RequestMapping(method = RequestMethod.POST,value = "/addPet")
    Boolean addPet(@RequestBody Pet pet);

    @RequestMapping(method = RequestMethod.POST,value = "/deletePet")
    Boolean deletePet(@RequestParam("petId") String petId);

    @RequestMapping(method = RequestMethod.POST,value = "/updatePet")
    Boolean updatePet(@RequestBody Pet pet);
}
