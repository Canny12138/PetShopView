package com.login.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.login.openFeign.CollectFeignService;
import com.login.openFeign.GoodFeignService;
import com.login.openFeign.StoreFeignService;
import com.soft.entity.Collect;
import com.soft.entity.Good;
import com.soft.ov.CollectOV;
import com.soft.util.JwtUtils;
import com.soft.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/4 9:50
 **/
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    CollectFeignService collectFeignService;
    @Autowired
    GoodFeignService goodFeignService;
    @Autowired
    StoreFeignService storeFeignService;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(method = RequestMethod.POST,value = "/getCollectPageByUserId")
    public Result getCollectPageByUserId(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        if(!tokenRes.getIsSuccess()){
            res.fail("未登录");
            return res;
        }
        String userId = JwtUtils.getUserIdByToken(token);
        Page<Collect> collectPage= collectFeignService.page(pageNum,pageSize,userId);
        List<Collect> records = collectPage.getRecords();
        List<CollectOV> resData = new LinkedList<>();
        if(records.size()==0){
            res.fail("无收藏");
            return res;
        }
        res.success("success");
        for(Collect collect:records){
            Good good = goodFeignService.getGoodById(collect.getGoodId());
            CollectOV temp = new CollectOV(good);
            String storeName = storeFeignService.getStoreById(good.getStoreId()).getStoreName();
            temp.setStoreName(storeName);
            if(collectFeignService.getIsCollect(JwtUtils.getUserIdByToken(token),good.getGoodId())){
                temp.setIsCollect(1);
            }else {
                temp.setIsCollect(0);
            }
            resData.add(temp);
        }
        res.setData(resData);
        res.setEtc(collectPage.getPages());
        return res;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deleteCollect")
    public Result deleteCollect(@RequestParam String goodId){
        Result res = new Result();
        String token = request.getHeader("token");
        Result tokenRes = JwtUtils.validateToken(token);
        if(!tokenRes.getIsSuccess()){
            res.fail("未登录");
            return res;
        }
        Collect collect = collectFeignService.getCollectByUserIdGoodId(JwtUtils.getUserIdByToken(token),goodId);
        if(collectFeignService.deleteCollect(collect.getCollectId())){
            res.success("删除成功");
            return res;
        }
        res.fail("删除失败");
        return res;
    }

}
