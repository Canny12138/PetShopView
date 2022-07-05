package com.soft.ov;

import com.soft.entity.User;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/4 8:30
 **/
public class TokenOV {
    private String nickname;
    private String token;
    public TokenOV(User user, String token){
        this.nickname = user.getNickname();
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
