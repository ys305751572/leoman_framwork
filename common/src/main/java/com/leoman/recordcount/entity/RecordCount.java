package com.leoman.recordcount.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/7/22 0022.
 */
@Entity
@Table(name = "t_record_count")
public class RecordCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visitCount")
    private Integer visitCount;

    @Column(name = "onlineCount")
    private Integer onlineCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }
}
