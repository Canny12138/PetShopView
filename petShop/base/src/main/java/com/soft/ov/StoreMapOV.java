package com.soft.ov;

import com.soft.entity.Store;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/5 15:52
 **/
public class StoreMapOV {
    private String storeId;
    private String storeName;
    private Double longitude;
    private Double latitude;
    public StoreMapOV(Store store){
        this.storeId = store.getStoreId();
        this.storeName = store.getStoreName();
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
}
