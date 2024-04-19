package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.Article;
import com.example.service.ArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: ArticleController
 * Package: com.example.controller
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/4 16:55
 * @Version 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {


    @Resource
    private ArticleService articleService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        articleService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        articleService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Article Article) {
        articleService.updateById(Article);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        System.out.println(id);
        Article article = articleService.selectById(id);
        System.out.println(article);
        return Result.success(article);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Article article) {
        List<Article> list = articleService.selectAll(article);
        return Result.success(list);
    }

    @GetMapping("/getRecommend")
    public Result getRecommend(@RequestParam String type) {
        System.out.println(type);
        Article article = articleService.getRecommend(type);
        return Result.success(article);
    }
    @GetMapping("/selectTop8")
    public Result selectTop8(@RequestParam String type) {
        List<Article> list = articleService.selectTop8(type);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "1945-3-7") String startDate,
                             @RequestParam(defaultValue = "2222-4-7") String endDate) {
        System.out.println(article);
        java.util.Date startdate1 = java.sql.Date.valueOf(startDate);
        java.util.Date enddate1 = java.sql.Date.valueOf(endDate);
        System.out.println(startDate);
        System.out.println(endDate);
        PageInfo<Article> page = articleService.selectPage(article, pageNum, pageSize, startdate1, enddate1);
        return Result.success(page);
    }
    @GetMapping("/selectAccPage")
    public Result selectAccPage(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "1945-3-7") String startDate,
                             @RequestParam(defaultValue = "2222-4-7") String endDate) {
        System.out.println(article);
        java.util.Date startdate1 = java.sql.Date.valueOf(startDate);
        java.util.Date enddate1 = java.sql.Date.valueOf(endDate);
        System.out.println(startDate);
        System.out.println(endDate);
        PageInfo<Article> page = articleService.selectAccPage(article, pageNum, pageSize, startdate1, enddate1);
        return Result.success(page);
    }
    @GetMapping("/selectPager")
    public Result selectPager(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "1945-3-7") String startDate,
                             @RequestParam(defaultValue = "2222-4-7") String endDate) {
        System.out.println(article);
        java.util.Date startdate1 = java.sql.Date.valueOf(startDate);
        java.util.Date enddate1 = java.sql.Date.valueOf(endDate);
        System.out.println(startDate);
        System.out.println(endDate);
        PageInfo<Article> page = articleService.selectPager(article, pageNum, pageSize, startdate1, enddate1);
        return Result.success(page);
    }
    @GetMapping("/selectArticle")
    public Result selectArticle(Article article,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Article> page=articleService.selectArticle(article,pageNum,pageSize);
        return Result.success(page);
    }
    @GetMapping("/getPie")
    public Result getPie() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        List<Article> ordersList = articleService.selectAll(new Article());
        Map<String, Long> collect = ordersList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getType()))
                .collect(Collectors.groupingBy(Article::getType, Collectors.counting()));
        for (String key : collect.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            map.put("value", collect.get(key));
            list.add(map);
        }

        resultMap.put("text", "平台文献中英占比统计（饼图）");
        resultMap.put("subText", "统计维度：是否为中文");
        resultMap.put("name", "占比数据");
        resultMap.put("data", list);

        return Result.success(resultMap);
    }


}
