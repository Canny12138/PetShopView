package com.soft.ov;

import com.soft.entity.Good;
import com.soft.entity.Store;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/26 18:36
 **/
public class GoodOV {
    private String goodId;
    private String goodName;
    private String img;
    private String storeName;
    private String storeId;
    private Double price;
    private Integer isCollect;
    public GoodOV(Good good){
        this.goodId = good.getGoodId();
        this.goodName = good.getGoodName();
        this.img = good.getImg();
        this.price = good.getPrice();
        this.storeId = good.getStoreId();
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public Double getPrice() {
        return price;
    }

    public String getGoodName() {
        return goodName;
    }

    public String getImg() {
        return img;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
