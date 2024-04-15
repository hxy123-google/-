package com.example.entity;

import java.io.Serializable;

/**
 * ClassName: Score
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/8 0:18
 * @Version 1.0
 */
public class Score implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private String img;
    private String name;
    private String content;
    private String type;
    private Integer price;
    private String video;
    private String file;
    private String recommend;
    private String time;
    private String aId;
    private String author;
    private String status;
    private String descr;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", video='" + video + '\'' +
                ", file='" + file + '\'' +
                ", recommend='" + recommend + '\'' +
                ", time='" + time + '\'' +
                ", aId='" + aId + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                ", descr='" + descr + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Score(Integer id, String img, String name, String content, String type, Integer price, String video, String file, String recommend, String time, String aId, String author, String status, String descr) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.content = content;
        this.type = type;
        this.price = price;
        this.video = video;
        this.file = file;
        this.recommend = recommend;
        this.time = time;
        this.aId = aId;
        this.author = author;
        this.status = status;
        this.descr = descr;
    }
}