package com.soft.ov;

import com.soft.entity.Address;
import com.soft.entity.Orders;
import com.soft.entity.OrdersStatus;
import com.soft.entity.UserAddress;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/7 12:01
 **/
public class OrderOV {
    private String orderId;
    private String createTime;
    private Integer orderStatus;
    private String orderStatusValue;
    private Double price;
    private String address;
    private String receiver;
    private String tel;

    public OrderOV(Orders orders, UserAddress address, OrdersStatus ordersStatus){
        this.orderId = orders.getOrdersId();
        this.createTime = orders.getCreateTime();
        this.price = orders.getPrice();
        this.address = address.getAddress();
        this.receiver = address.getReceiver();
        this.tel = address.getTel();
        this.orderStatusValue = ordersStatus.getStatus();

    }
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusValue() {
        return orderStatusValue;
    }

    public void setOrderStatusValue(String orderStatusValue) {
        this.orderStatusValue = orderStatusValue;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
