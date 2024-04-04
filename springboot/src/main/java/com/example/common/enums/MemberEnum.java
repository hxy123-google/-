package com.example.common.enums;

/**
 * ClassName: MemberEnum
 * Package: com.example.common.enums
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/4 1:10
 * @Version 1.0
 */
public enum MemberEnum {
    YES("会员"),
    NO("非会员"),
    ;
    public String info;

    MemberEnum(String info) {
        this.info = info;
    }
}
