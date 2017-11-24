package com.icooding.cms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * project_name icooding-cms
 * class RegesterCode
 * date  2017/11/24
 * author ibm
 * version 1.0
 */
@Entity
@Table(name = "t_regester_code")
public class RegesterCode implements Serializable{

    private static final long serialVersionUID = -1628664115629127179L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 注册码
     */
    @Column(name = "code")
    private String code;


    /**
     * 状态 1:未激活 0:已激活
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 激活用户
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * 激活用户nickname
     */
    @Column(name = "username")
    private String username;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
