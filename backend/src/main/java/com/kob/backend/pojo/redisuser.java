package com.kob.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: redisuser
 * Package: com.kob.backend.pojo
 * Description:
 *
 * @Author Hxy
 * @Create 2024/2/29 10:05
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class redisuser implements Serializable {
    private String username;
    private Integer userid;
    private Integer rating;
}
