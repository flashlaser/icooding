package com.icooding.cms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * project_name icooding-cms
 * class BrowseLog
 * date  2017/11/28
 * author ibm
 * version 1.0
 */
@Entity
@Table(name = "t_browse_log")
public class BrowseLog implements Serializable{

    private static final long serialVersionUID = -1266577110427165607L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;// 操作日期

    @Column(name = "referer")
    private String referer;// 操作用户

    @Column(name = "link")
    private String link;//操作用户id;

    @Column(name = "ip")
    private String ip;// 操作IP

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
