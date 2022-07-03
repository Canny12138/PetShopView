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
@TableName("orders")
public class Orders {
    @TableId
    private String ordersId;
    private String createTime;
    private Integer ordersStatus;
    private String userId;
}
