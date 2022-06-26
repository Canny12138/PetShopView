package com.soft.ov;

import com.soft.entity.Good;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/26 18:36
 **/
public class GoodOV {
    private Good good;
    private Object thing;
    private Object thingInfo;
    public  GoodOV(Good good){
        this.good = good;
    }

    public Good getGood() {
        return good;
    };

    public void setGood(Good good) {
        this.good = good;
    }

    public Object getThing() {
        return thing;
    }

    public Object getThingInfo() {
        return thingInfo;
    }

    public void setThing(Object thing) {
        this.thing = thing;
    }

    public void setThingInfo(Object thingInfo) {
        this.thingInfo = thingInfo;
    }
}
