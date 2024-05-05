package com.example.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Article;
import com.example.entity.Bycited;
import com.example.entity.CategoryMaterial;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.BycitedMapper;
import com.example.service.ArticleService;
import com.example.service.BycitedService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * ClassName: Bycited
 * Package: com.example.controller
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/18 15:26
 * @Version 1.0
 */
@RestController
public class BycitedController {
    @Resource
    private BycitedService bycitedService;
    @Resource
    private ArticleService articleService;
    @GetMapping("/bycited/add")
    public Result add(Bycited bycited,
                      @RequestParam(defaultValue = "0") Integer userId){
        Bycited  bycited1=new Bycited();
        Article article=articleService.selectById(bycited.getById());

        if (ObjectUtil.isNull(article)) {
            throw new CustomException(ResultCodeEnum.CITE_NOT_EXIST);
        }
        article=articleService.selectById(bycited.getCiteId());
        if (ObjectUtil.isNull(article)) {
            throw new CustomException(ResultCodeEnum.ARTICLE_NOT_EXIST);
        }
        bycited1.setCiteId(bycited.getCiteId());
        bycited1.setById(bycited.getCiteId());
        List<Bycited> list=bycitedService.selectAll(bycited);
        if(list.size()>0)  throw new CustomException(ResultCodeEnum.CITE_ALREADY_EXIST);
        bycitedService.add(bycited);
        article=articleService.selectById(bycited.getById());
        article.setReference(article.getReference()+1);
        articleService.updateById(article);
        return Result.success();
    }
    @GetMapping("/bycited/selectPager")
    public Result selectPager(Bycited bycited,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Article> page = bycitedService.selectPager(bycited, pageNum, pageSize);
        return Result.success(page);
    }
    @GetMapping("bycited/getProBar")
    public Result getProBar(User user){
        Map<String, Object> resultMap = new HashMap<>();
        List<String> xList = new ArrayList<>();
        List<Long> yList = new ArrayList<>();
        Map<String, Long> resultMap1 = new HashMap<>();
        xList.add("2019");
        xList.add("2020");
        xList.add("2021");
        xList.add("2022");
        xList.add("2023");
        xList.add("2024");
        List<Article>article=articleService.getByAuthorId(user);
        for(int i=0;i<article.size();i++){
            Bycited bycited=new Bycited();
            bycited.setById(article.get(i).getId());
            List<Bycited> courses = bycitedService.selectAll(bycited);
            for(int j=0;j<courses.size();j++) {
                if(resultMap1.get(courses.get(j).getYear())!=null)
                    resultMap1.put(courses.get(j).getYear(), resultMap1.get(courses.get(j).getYear()) + 1);
                else resultMap1.put(courses.get(j).getYear(), Long.valueOf(1));
            }
        }
        if(resultMap1.get("2019")!=null) yList.add(resultMap1.get("2019"));
        else yList.add(Long.valueOf(0));
        if(resultMap1.get("2020")!=null) yList.add(resultMap1.get("2020"));
        else yList.add(Long.valueOf(0));
        if(resultMap1.get("2021")!=null) yList.add(resultMap1.get("2021"));
        else yList.add(Long.valueOf(0));
        if(resultMap1.get("2022")!=null) yList.add(resultMap1.get("2022"));
        else yList.add(Long.valueOf(0));
        if(resultMap1.get("2023")!=null) yList.add(resultMap1.get("2023"));
        else yList.add(Long.valueOf(0));
        if(resultMap1.get("2024")!=null) yList.add(resultMap1.get("2024"));
        else yList.add(Long.valueOf(0));
        resultMap.put("text", "平台论文被引用数统计（柱状图）");
        resultMap.put("subText", "统计维度：被引用数");
        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);

        return Result.success(resultMap);
    }
    @GetMapping("/bycited/getArticleBar")
    public Result getBar(Bycited bycited) {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> xList = new ArrayList<>();
        List<Long> yList = new ArrayList<>();

        xList.add("2019");
        xList.add("2020");
        xList.add("2021");
        xList.add("2022");
        xList.add("2023");
        xList.add("2024");

        List<Bycited> courses = bycitedService.selectAll(bycited);
        yList.add(courses.stream().filter(x -> "2019".equals(x.getYear())).count());
        yList.add(courses.stream().filter(x -> "2020".equals(x.getYear())).count());
        yList.add(courses.stream().filter(x -> "2021".equals(x.getYear())).count());
        yList.add(courses.stream().filter(x -> "2022".equals(x.getYear())).count());
        yList.add(courses.stream().filter(x -> "2023".equals(x.getYear())).count());
        yList.add(courses.stream().filter(x -> "2024".equals(x.getYear())).count());
        resultMap.put("text", "各个年份被引用总数统计（柱状图）");
        resultMap.put("subText", "统计维度：引用数");
        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);

        return Result.success(resultMap);
    }
}
