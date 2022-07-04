package com.storeService.openFeign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft.entity.Collect;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="userManage-service",path = "/collect")
public interface CollectFeignService {
    @RequestMapping(method = RequestMethod.POST,value = "/getCollectByUserId")
    List<Collect> getCollectByUserId(@RequestParam("userId") String userId);
    @RequestMapping(method = RequestMethod.POST,value = "getIsCollect")
    Boolean getIsCollect(@RequestParam("userId") String userId,@RequestParam("goodId") String goodId);
    @RequestMapping(method = RequestMethod.POST,value = "/addCollect")
    Boolean addCollect(@RequestBody Collect collect);
    @RequestMapping(method = RequestMethod.POST,value = "/deleteCollect")
    Boolean deleteCollect(@RequestParam("collectId") String collectId);
    @RequestMapping(method = RequestMethod.POST,value = "/updateCollect")
    Boolean updateCollect(@RequestBody Collect collect);
    @RequestMapping(method = RequestMethod.POST,value = "/getCollectByUserIdGoodId")
    Collect getCollectByUserIdGoodId(
            @RequestParam("userId") String userId,
            @RequestParam("goodId") String goodId
    );
    @RequestMapping(method = RequestMethod.POST,value = "/getCollectPageByUserId")
    Page<Collect> page(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("userId") String userId
    );
}
