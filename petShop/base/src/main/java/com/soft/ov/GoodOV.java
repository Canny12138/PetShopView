package com.soft.ov;

import com.soft.entity.Good;
import com.soft.entity.Store;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/26 18:36
 **/
public class GoodOV {
    private Good good;
    private Store store;
    private Object type;
    public  GoodOV(Good good){
        this.good = good;
    }

    public Good getGood() {
        return good;
    };

    public void setGood(Good good) {
        this.good = good;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getType() {
        return type;
    }
}
