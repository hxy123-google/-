package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Article;
import com.example.entity.Bycited;
import com.example.entity.Collect;
import com.example.exception.CustomException;
import com.example.service.CollectService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ClassName: CollectController
 * Package: com.example.controller
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/18 20:23
 * @Version 1.0
 */
@RestController
public class CollectController {
    @Resource
    private CollectService collectService;
    @GetMapping("/collection/add/")
    Result add(Collect collect){
        Collect collect1=collectService.selectById(collect);
        if (!ObjectUtil.isNull(collect1)) {
            throw new CustomException(ResultCodeEnum.Collection_ALREADT_EXIST);
        }
        collectService.add(collect);
        return Result.success();
    }
    @GetMapping("/collection/selectPager")
    public Result selectPager(Collect collect ,
                              @RequestParam(defaultValue ="") String category,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Article> page = collectService.selectPager(collect, category,pageNum, pageSize);
        return Result.success(page);
    }
    @GetMapping("/collection/delete")
    public Result deletbyId(Collect collect){
        collectService.deletbyId(collect);
        return Result.success();
    }
}
