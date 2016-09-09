package com.leoman.system.entity;

/**
 * Created by Administrator on 2016/9/7.
 */
public class TestEntity {

    private String username;

    private String password;

    public TestEntity(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
