package com.leoman.sohuapi.entity;

/**
 * Created by Administrator on 2016/8/15.
 */
public class Result {

    private Integer status;

    private String statusText;

    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "status :" + getStatus() + "==statusText:" + getStatusText() + "==data :" + getData();
    }
}
