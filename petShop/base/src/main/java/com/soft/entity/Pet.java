package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/23 14:42
 **/
@Data
@TableName("pet")
public class Pet {
    @TableId
    private String petId;
    private String petName;
    private int petType;

}
