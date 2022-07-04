package com.soft.ov;

import com.soft.entity.Good;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/4 16:47
 **/
public class CartOV {
    private String goodId;
    private String goodName;
    private String img;
    private String storeName;
    private Double price;
    private Integer isCollect;
    public CartOV(Good good){
        this.goodId = good.getGoodId();
        this.goodName = good.getGoodName();
        this.img = good.getImg();
        this.price = good.getPrice();
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
