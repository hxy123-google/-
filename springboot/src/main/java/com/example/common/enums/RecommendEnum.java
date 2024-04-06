package com.example.common.enums;

/**
 * ClassName: RecommendEnum
 * Package: com.example.common.enums
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/6 22:08
 * @Version 1.0
 */
public enum RecommendEnum {
    YES("是"),
    NO("否")
    ;
    public String status;
    RecommendEnum(String status){
        this.status=status;
    }
}
