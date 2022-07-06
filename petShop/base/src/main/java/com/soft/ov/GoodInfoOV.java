package com.soft.ov;

import com.soft.entity.Good;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/30 8:55
 **/
public class GoodInfoOV {
    private String goodId;
    private String goodName;
    private String img;
    private String goodInfo;
    private String type;
    private Double price;
    private String storeName;
    private String storeId;
    private Integer isCollect;
    public GoodInfoOV(Good good){
        this.goodId = good.getGoodId();
        this.goodName = good.getGoodName();
        this.img = good.getImg();
        this.price = good.getPrice();
        this.storeId = good.getStoreId();
    }

    public Object getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGoodInfo() {
        return goodInfo;
    }

    public void setGoodInfo(String goodInfo) {
        this.goodInfo = goodInfo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }
}
