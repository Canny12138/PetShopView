package com.storeService.openFeign;

import com.soft.entity.SurroundingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 15:26
 **/
@FeignClient(name="surroundingManage-service",path = "/surroundingInfo")
public interface SurroundingInfoFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getInfoById")
    SurroundingInfo getPetInfoById(@RequestParam("surroundingId") String surroundingId);

    @RequestMapping(method = RequestMethod.POST,value = "/addInfo")
    Boolean addSurroundingInfo(@RequestBody SurroundingInfo surroundingInfo);

    @RequestMapping(method = RequestMethod.POST,value = "/deleteInfo")
    Boolean deleteSurroundingInfo(@RequestParam("infoId") String infoId);

    @RequestMapping(method = RequestMethod.POST,value = "/updateInfo")
    Boolean updateSurroundingInfo(@RequestBody SurroundingInfo surroundingInfo);
}
