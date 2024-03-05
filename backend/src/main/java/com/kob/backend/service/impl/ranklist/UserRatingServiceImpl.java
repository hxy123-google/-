package com.kob.backend.service.impl.ranklist;

import com.kob.backend.service.ranklist.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * ClassName: UserRatingServiceImpl
 * Package: com.kob.backend.service.impl.ranklist
 * Description:
 *
 * @Author Hxy
 * @Create 2024/2/29 10:36
 * @Version 1.0
 */
public class UserRatingServiceImpl implements UserRatingService {
    @Autowired
    private RedisTemplate redisTemplate;
}
