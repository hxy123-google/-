package com.kob.backend.controller.ranklist;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.service.ranklist.GetRanklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetRanklistController {
    @Autowired
    private GetRanklistService getRanklistService;
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/ranklist/getlist/")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        System.out.println(11);
        Integer page = Integer.parseInt(data.get("page"));
        return getRanklistService.getList(page);
    }
}
