package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: ImSingle
 * Package: com.example.entity
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/12 23:36
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImSingle {
    private Integer id;
    /** 内容 */
    private String content;
    /** 发送人 */
    private String fromuser;
    /** 发送人头像 */
    private String fromavatar;
    /** 时间 */
    private String time;
    /** 类型 */
    private String type;
    /** 接收人 */
    private String touser;
    /** 接收人头像 */
    private String toavatar;
    /** 是否已读 */
    private Integer readed;


}
