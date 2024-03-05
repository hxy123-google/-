package com.kob.backend.controller.record;

import com.kob.backend.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;

/**
 * ClassName: GetRecordListController
 * Package: com.kob.backend.controller.record
 * Description:
 *
 * @Author Hxy
 * @Create 2024/2/27 22:06
 * @Version 1.0
 */
@RestController
public class GetRecordListController {
    @Autowired
    private GetRecordListService getRecordListService;
    @GetMapping("/record/getlist/")
    public JSONObject getList(@RequestParam Map<String,String> data){
        Integer page = Integer.parseInt(data.get("page"));
        return getRecordListService.getList(page);

    }
}
