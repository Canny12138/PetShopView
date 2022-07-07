package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/23 15:13
 **/
@Data
@TableName("store")
public class Store {
    @TableId
    private String storeId;
    private String storeName;
    private String addressId;
    private String info;
    private Integer storeRank;
}
