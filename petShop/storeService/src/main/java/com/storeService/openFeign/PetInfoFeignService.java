package com.storeService.openFeign;

import com.soft.entity.PetInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 15:24
 **/
@FeignClient(name="petManage-service",path = "/petInfo")
public interface PetInfoFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getInfoById")
    PetInfo getPetInfoById(@RequestParam("petId") String petId);

    @RequestMapping(method = RequestMethod.POST,value = "/addInfo")
    @ResponseBody
    Boolean addPetInfo(@RequestBody PetInfo petInfo);

    @RequestMapping(method = RequestMethod.POST,value = "/deleteInfo")
    Boolean deletePetInfo(@RequestParam("infoId") String infoId);

    @RequestMapping(method = RequestMethod.POST,value = "/updateInfo")
    Boolean updatePetInfo(@RequestBody PetInfo petInfo);
}
