package com.soft.ov;

import com.soft.entity.Address;
import com.soft.entity.Store;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/6 21:05
 **/
public class StoreInfoOV {
    private String storeId;
    private String storeName;
    private Double longitude;
    private Double latitude;
    private String address;
    private String info;
    private Integer rank;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public StoreInfoOV(Store store, Address address){
        this.storeId = store.getStoreId();
        this.storeName = store.getStoreName();
        this.longitude = address.getLongitude();
        this.latitude = address.getLatitude();
        this.address = address.getAddress();
        this.info = store.getInfo();
        this.rank = store.getRank();
    }
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
