package com.example.entity;

import java.io.Serializable;

/**
 * ClassName: Orders
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/16 9:36
 * @Version 1.0
 */
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private Integer articleId;
    private Double price;
    private String orderId;
    private String time;
    private Integer userId;
    private String ArticleImg;
    private String ArticleName;
    private String userName;
    private String author;
    String uploader;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getArticleImg() {
        return ArticleImg;
    }

    public void setArticleImg(String articleImg) {
        ArticleImg = articleImg;
    }

    public String getArticleName() {
        return ArticleName;
    }

    public void setArticleName(String articleName) {
        ArticleName = articleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
}