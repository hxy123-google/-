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
@Data
@AllArgsConstructor
@NoArgsConstructor
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



}
