package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/4 15:04
 **/
@Data
@TableName("cart")
public class Cart {
    @TableId
    private String cartId;
    private String userId;
    private String goodId;
    private String addTime;
}
