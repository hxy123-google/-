package com.example.common.enums;

public enum ResultCodeEnum {
    SUCCESS("200", "成功"),

    PARAM_ERROR("400", "参数异常"),
    TOKEN_INVALID_ERROR("401", "无效的token"),
    TOKEN_CHECK_ERROR("401", "token验证失败，请重新登录"),
    PARAM_LOST_ERROR("4001", "参数缺失"),

    SYSTEM_ERROR("500", "系统异常"),
    USER_EXIST_ERROR("5001", "用户名已存在"),
    USER_NOT_LOGIN("5002", "用户未登录"),
    USER_ACCOUNT_ERROR("5003", "账号或密码错误"),
    USER_NOT_EXIST_ERROR("5004", "用户不存在"),
    PARAM_PASSWORD_ERROR("5005", "原密码输入错误"),
    RECOMMEND_ALREADY_ERROR("5006","已经有推荐的功能了，请先下架一个"),
    SCORE_LOWER_ERROR("5007","积分不够"),
    ACCOUNT_LOWER_ERROR("5008","文章积分不够"),
    SIGNIN_ALREADY_ERROR("5009","已经签过了"),
    CITE_ALREADY_EXIST("5010","这文章应经引过了"),
    CITE_NOT_EXIST("5011","引用文章不存在"),
    ARTICLE_NOT_EXIST("5012","你的文章未上传本系统"),
    YOU_NOT_AUTHOR("5013","你不是作者"),
    Collection_ALREADT_EXIST("5014","你已收藏过了"),
    ;

    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
