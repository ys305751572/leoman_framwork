package com.leoman.giftrecord.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_gift_exchange_record")
@Entity
public class GiftExchangeRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "gift_name")
    private String giftName;

    @Column(name = "exchange_coin")
    private Integer exchangeCoin;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_mobile")
    private String receiverMobile;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "status")
    private Integer status;

    @Column(name = "courier_name")
    private String courierName;

    @Column(name = "courier_sn")
    private String courierSn;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getExchangeCoin() {
        return exchangeCoin;
    }

    public void setExchangeCoin(Integer exchangeCoin) {
        this.exchangeCoin = exchangeCoin;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierSn() {
        return courierSn;
    }

    public void setCourierSn(String courierSn) {
        this.courierSn = courierSn;
    }
}
