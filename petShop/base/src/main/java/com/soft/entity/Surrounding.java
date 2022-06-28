package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/23 17:07
 **/
@Data
@TableName("surrounding")
public class Surrounding {
    @TableId
    private String surroundingId;
    private String surroundingName;
    private Integer surroundingType;
}
