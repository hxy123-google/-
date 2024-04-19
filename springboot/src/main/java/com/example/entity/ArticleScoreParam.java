package com.example.entity;

import java.io.Serializable;

/**
 * ClassName: ArticleScoreParam
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/19 10:54
 * @Version 1.0
 */

public class ArticleScoreParam implements Serializable {
    private Integer scoreId;

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    private Integer aId1;
    private Integer aId2;
    private Integer aId3;

    public Integer getaId1() {
        return aId1;
    }

    public void setaId1(Integer aId1) {
        this.aId1 = aId1;
    }

    public Integer getaId2() {
        return aId2;
    }

    public void setaId2(Integer aId2) {
        this.aId2 = aId2;
    }

    public Integer getaId3() {
        return aId3;
    }

    public void setaId3(Integer aId3) {
        this.aId3 = aId3;
    }
}
