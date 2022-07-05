package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:42
 **/
@Data
@TableName("orders_good")
public class OrdersGood {
    @TableId
    private String id;
    private String ordersId;
    private String goodId;
}
