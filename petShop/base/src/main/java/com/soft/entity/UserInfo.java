package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/5 22:56
 **/
@Data
@TableName("user_info")
public class UserInfo {
    @TableId
    private String infoId;
    private String userId;
    private String defAddress;
    private String userImg;
    private String mail;
    private String tel;
}
