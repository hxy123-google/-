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
    private Integer articleId;
    private Integer scoreId;

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

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }
}
