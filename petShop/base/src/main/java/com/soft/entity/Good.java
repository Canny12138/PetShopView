package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/23 15:12
 **/
@Data
@TableName("good")
public class Good {
    @TableId
    private String goodId;
    private String goodName;
    private int type;
    private String storeId;
    private String thingId;
    private int stock;
    private double price;
    private Boolean useful;
}
