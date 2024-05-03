package com.example.entity;

import java.io.Serializable;

/**
 * ClassName: menu
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/5/3 20:37
 * @Version 1.0
 */
public class Menu implements Serializable {
    private Integer id;
    private Integer userId;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
