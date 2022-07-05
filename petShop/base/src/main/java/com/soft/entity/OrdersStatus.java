package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/5 9:08
 **/
@TableName("orders_status")
@Data
public class OrdersStatus {
    @TableId
    private String statusId;
    private Integer statusValue;
    private String status;
}
