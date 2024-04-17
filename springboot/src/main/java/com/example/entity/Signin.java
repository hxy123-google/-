package com.example.entity;

import java.io.Serializable;

/**
 * ClassName: Signin
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/17 21:46
 * @Version 1.0
 */
public class Signin implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private Integer userId;
    private String time;
    private String day;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
