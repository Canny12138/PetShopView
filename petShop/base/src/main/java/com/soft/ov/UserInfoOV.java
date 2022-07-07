package com.soft.ov;

import com.soft.entity.User;
import com.soft.entity.UserInfo;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/7/6 17:53
 **/
public class UserInfoOV {
    private String userId;
    private String nickname;
    private String defAddress;
    private String userImg;
    private String mail;
    private String tel;

    public UserInfoOV(User user, UserInfo userInfo){
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.userImg = userInfo.getUserImg();
        this.mail = userInfo.getMail();
        this.tel = userInfo.getTel();
    }
    public UserInfoOV(User user){
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDefAddress() {
        return defAddress;
    }

    public void setDefAddress(String defAddress) {
        this.defAddress = defAddress;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
