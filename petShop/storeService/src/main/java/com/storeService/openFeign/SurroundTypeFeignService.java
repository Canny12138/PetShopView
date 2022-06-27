package com.storeService.openFeign;

import com.soft.entity.SurroundingType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="surroundingManage-service",path = "/surroundingType")
public interface SurroundTypeFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getSurroundingType")
    SurroundingType getSurroundingTypeByTypeValue(@RequestParam("typeValue") Integer typeValue);

    @RequestMapping(method = RequestMethod.POST,value = "/addSurroundingType")
    Boolean addSurroundingType(@RequestBody SurroundingType surroundingType);

    @RequestMapping(method = RequestMethod.POST,value = "/deleteSurroundingType")
    Boolean deleteSurroundingType(@RequestParam("typeId") String typeId);

    @RequestMapping(method = RequestMethod.POST,value = "/updateSurroundingType")
    Boolean updatePetType(@RequestBody SurroundingType surroundingType);
}
