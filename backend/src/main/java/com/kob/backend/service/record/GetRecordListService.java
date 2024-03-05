package com.kob.backend.service.record;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * ClassName: GetRecordListService
 * Package: com.kob.backend.service.record
 * Description:
 *
 * @Author Hxy
 * @Create 2024/2/27 21:59
 * @Version 1.0
 */
public interface GetRecordListService {
    public JSONObject getList(Integer data);

}
