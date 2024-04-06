package com.example.entity;

/**
 * ClassName: categorymaterial
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/7 0:18
 * @Version 1.0
 */
public class CategoryMaterial {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoryMaterial(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getCategory() {
        return name;
    }

    public void setCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "categorymaterial{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
