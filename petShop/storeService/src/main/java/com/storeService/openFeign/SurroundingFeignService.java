package com.storeService.openFeign;

import com.soft.entity.Surrounding;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="surroundingManage-service",path = "/surrounding")
public interface SurroundingFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getSurroundingById")
    Surrounding getSurroundingBySurroundingId(@RequestParam("surroundingId") String SurroundingId);

    @RequestMapping(method = RequestMethod.POST,value = "/addSurrounding")
    Boolean addSurrounding(@RequestBody Surrounding surrounding);

    @RequestMapping(method = RequestMethod.POST,value = "/deleteSurrounding")
    Boolean deleteSurrounding(@RequestParam("surroundingId") String surroundId);

    @RequestMapping(method = RequestMethod.POST,value = "/updateSurrounding")
    Boolean updateSurrounding(@RequestBody Surrounding surrounding);
}
