package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/23 14:44
 **/
@Data
@TableName("pet_type")
public class PetType {
    @TableId
    private String typeId;
    private Integer typeValue;
    private String type;
}
