package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/28 15:46
 **/
@Data
@TableName("address")
public class Address {
    @TableId
    private String addressId;
    private String address;
    private Double longitude;
    private Double latitude;
}
