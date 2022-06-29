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
    private String collect_id;
    private String user_id;
    private String good_id;
    private String add_time;
}
