package com.storeService.openFeign;

import com.soft.entity.SurroundingInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 15:26
 **/
@FeignClient(name="surroundingManage-service",path = "/surroundingInfo")
public interface SurroundingInfoFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getInfoById")
    SurroundingInfo getSurroundingInfoById(@RequestParam("surroundingId") String surroundingId);

    @RequestMapping(method = RequestMethod.POST,value = "/addInfo")
    @ResponseBody
    Boolean addSurroundingInfo(@RequestBody SurroundingInfo surroundingInfo);

    @RequestMapping(method = RequestMethod.POST,value = "/deleteInfo")
    Boolean deleteSurroundingInfo(@RequestParam("infoId") String infoId);

    @RequestMapping(method = RequestMethod.POST,value = "/updateInfo")
    Boolean updateSurroundingInfo(@RequestBody SurroundingInfo surroundingInfo);
}
