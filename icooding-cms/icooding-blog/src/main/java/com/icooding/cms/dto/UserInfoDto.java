package com.icooding.cms.dto;

import java.io.Serializable;

/**
 * Created by jagua on 2017/11/17.
 */
public class UserInfoDto implements Serializable {

    public int is_login;//是否登录，0表示未登录，1表示已登录

    public UserDto user; //用户信息

    public int getIs_login() {
        return is_login;
    }

    public void setIs_login(int is_login) {
        this.is_login = is_login;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

}