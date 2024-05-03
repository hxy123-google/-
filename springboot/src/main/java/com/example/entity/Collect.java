package com.example.entity;

import java.io.Serializable;

/**
 * ClassName: Collection
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/18 19:07
 * @Version 1.0
 */
public class Collect implements Serializable {
    private Integer id;
    private Integer articleId;
    private Integer cId;
    private String name;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
