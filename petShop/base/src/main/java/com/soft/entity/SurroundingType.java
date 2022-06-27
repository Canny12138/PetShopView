package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 9:21
 **/
@Data
@TableName("surrounding_type")
public class SurroundingType {
    @TableId
    private String typeId;
    private int typeValue;
    private String type;
}
