package com.example.entity;

import java.io.Serializable;

/**
 * ClassName: ArticleScore
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/19 1:25
 * @Version 1.0
 */
public class ArticleScore implements Serializable {
    private Integer id;
    private Integer articleOId;
    private Integer scoreOId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleOId() {
        return articleOId;
    }

    public void setArticleOId(Integer articleOId) {
        this.articleOId = articleOId;
    }

    public Integer getScoreOId() {
        return scoreOId;
    }

    public void setScoreOId(Integer scoreOId) {
        this.scoreOId = scoreOId;
    }
}
