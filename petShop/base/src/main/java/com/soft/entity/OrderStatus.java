package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 11:36
 **/
@Data
@TableName("order_status")
public class OrderStatus {
    private String statusId;
    private Integer statusValue;
    private String status;

}
