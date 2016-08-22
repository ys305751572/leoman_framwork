package com.leoman.report.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.post.entity.PostBase;
import com.leoman.user.entity.UserInfo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_report")
@Entity
public class Report extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(name = "report_type")
    private String reportType;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostBase postBase;

    @Column(name = "status")
    private Integer status;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public PostBase getPostBase() {
        return postBase;
    }

    public void setPostBase(PostBase postBase) {
        this.postBase = postBase;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
