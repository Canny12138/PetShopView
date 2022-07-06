package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/5 20:48
 **/
@Data
@TableName("user_address")
public class UserAddress {
    @TableId
    private String addressId;
    private String address;
    private String userId;
}
