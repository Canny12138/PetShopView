package com.soft.ov;

import com.soft.entity.Address;
import com.soft.entity.UserAddress;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/6 22:35
 **/
public class UserAddressOV {
    private String addressId;
    private String address;
    private String receiver;
    private String tel;
    private Integer isDef;

    public UserAddressOV(UserAddress userAddress){
        this.addressId = userAddress.getAddressId();
        this.address = userAddress.getAddress();
        this.receiver = userAddress.getReceiver();
        this.tel = userAddress.getTel();
        this.isDef = 0;
    }

    public Integer getIsDef() {
        return isDef;
    }

    public void setIsDef(Integer isDef) {
        this.isDef = isDef;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
