package com.soft.entity;

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
@TableName("order_good")
public class OrderGood {
    private String id;
    private String orderId;
    private String goodId;
}
