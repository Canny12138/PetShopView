package com.storeService.openFeign;

import com.soft.entity.PetType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="petManage-service",path = "/petType")
public interface PetTypeFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getPetType")
    PetType getPetTypeByTypeValue(@RequestParam("typeValue") Integer typeValue);

    @RequestMapping(method = RequestMethod.POST,value = "/addPetType")
    Boolean addPetType(@RequestBody PetType petType);

    @RequestMapping(method = RequestMethod.POST,value = "/deletePetType")
    Boolean deletePetType(@RequestParam("typeId") String typeId);

    @RequestMapping(method = RequestMethod.POST,value = "/updatePetType")
    Boolean updatePetType(@RequestBody PetType petType);
}
