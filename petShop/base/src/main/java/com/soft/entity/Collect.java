package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/29 10:22
 **/
@Data
@TableName("collect")
public class Collect {
    @TableId
    private String collecId;
    private String userId;
    private String goodId;
    private String addTime;
}
