package com.soft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/21 15:33
 **/
@Data
@TableName("user")
public class User {
    @TableId
    private String userId;

    private String username;
    private String password;
    private String nickname;
    private Integer userVip;
}
