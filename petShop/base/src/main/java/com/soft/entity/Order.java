package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:33
 **/
@Data
@TableName("order")
public class Order {
    @TableId
    private String orderId;
    private String create_time;
    private Integer order_status;
}
