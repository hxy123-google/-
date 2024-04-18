package com.example.entity;

import java.io.Serializable;

/**
 * ClassName: bycited
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/18 15:24
 * @Version 1.0
 */
public class Bycited implements Serializable {
    private Integer id;
    private Integer citeId;
    private Integer byId;
    private String time;
    private String year;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCiteId() {
        return citeId;
    }

    public void setCiteId(Integer citeId) {
        this.citeId = citeId;
    }

    public Integer getById() {
        return byId;
    }

    public void setById(Integer byId) {
        this.byId = byId;
    }
}
