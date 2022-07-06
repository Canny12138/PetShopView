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

    public UserAddressOV(UserAddress userAddress){
        this.addressId = userAddress.getAddressId();
        this.address = userAddress.getAddress();
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

    public void setAddress(String address) {
        this.address = address;
    }
}
