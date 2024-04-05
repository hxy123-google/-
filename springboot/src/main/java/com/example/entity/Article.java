package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: Article
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/4 16:41
 * @Version 1.0
 */

public class Article {


    /** ID */
    private Integer id;
    private String img;
    private String name;
    private String content;
    private String type;
    private Double price;
    private String video;
    private String file;
    private Double discount;
    private String recommend;
    private Integer reference;
    private Date time;
    private String author;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Article(Integer id, String img, String name, String content, String type, Double price, String video, String file, Double discount, String recommend, Integer reference, Date time, String author) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.content = content;
        this.type = type;
        this.price = price;
        this.video = video;
        this.file = file;
        this.discount = discount;
        this.recommend = recommend;
        this.reference = reference;
        this.time = time;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", video='" + video + '\'' +
                ", file='" + file + '\'' +
                ", discount=" + discount +
                ", recommend='" + recommend + '\'' +
                ", reference=" + reference +
                ", time=" + time +
                ", author='" + author + '\'' +
                '}';
    }
}
