package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 14:27
 **/
@Data
@TableName("pet_info")
public class PetInfo {
    @TableId
    private String infoId;
    private String petId;
    private String info;
}
