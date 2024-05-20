package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Article;
import com.example.entity.Orders;
import com.example.entity.UserArticle;
import com.example.exception.CustomException;
import com.example.service.ArticleService;
import com.example.service.OrdersService;
import com.example.service.UserArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserArticleController
 * Package: com.example.controller
 * Description:
 *
 * @Author Hxy
 * @Create 2024/5/5 18:43
 * @Version 1.0
 */
@RestController
public class UserArticleController {
    @Resource
    private UserArticleService userArticleService;
    @Resource
    private ArticleService articleService;
    @Resource
    private OrdersService ordersService;
    @PostMapping("Userarticle/add")
    public Result add(@RequestBody UserArticle userArticle){
        if(userArticle.getArticleId()!=-1) {
            Orders orders=new Orders();
            orders.setArticleId(userArticle.getArticleId());
            orders.setUserId(userArticle.getcId());
            List<Orders> list=ordersService.selectAll(orders);
            Article article = articleService.selectById(userArticle.getArticleId());
            if(list.size()==0&&article.getPrice()!=0) throw new CustomException(ResultCodeEnum.Not_buy);
            userArticle.setJournal(article.getJournal());
            userArticle.setKeywords(article.getKeywords());
            userArticle.setAuthor(article.getAuthor());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            userArticle.setTime(sdf.format(article.getTime()));
            userArticle.setFile(article.getFile());
            userArticle.setImg(article.getImg());
            userArticle.setType(article.getType());
            userArticle.setArticlename(article.getName());
        }else{

        }
        userArticleService.add(userArticle);
        return Result.success();
    }
    @GetMapping("Userarticle/selectAll")
    public Result selectAll(UserArticle userArticle,
                            @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<UserArticle> page = userArticleService.selectPager( userArticle,pageNum, pageSize);
        return Result.success(page);

    }
    @GetMapping("Userarticle/delete")
    public Result delete(UserArticle userArticle){
        userArticleService.delete(userArticle);
        return Result.success();
    }
    @GetMapping("Userarticle/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        UserArticle userArticle=userArticleService.selectById(id);
        return Result.success(userArticle);
    }
    @PostMapping("Userarticle/edit/")
    public Result edit(@RequestBody UserArticle userArticle){
        userArticleService.edit(userArticle);
        return Result.success();
    }
    @GetMapping("/Userarticle/getBar/")
    public Result getBar(UserArticle userArticle) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String,Integer> resultMap1=new HashMap<>();
        List<String> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();
        List<UserArticle>list=userArticleService.selectAll(userArticle);
        for(int i=0;i<list.size();i++){
            if(resultMap1.get(list.get(i).getName())==null)
                resultMap1.put(list.get(i).getName(),1);
            else resultMap1.put(list.get(i).getName(),resultMap1.get(list.get(i).getName())+1);
        }
        for (String key : resultMap1.keySet()) {
            xList.add(key);
            yList.add(resultMap1.get(key));
            //System.out.println("Key: " + key + ", Value: " + resultMap1.get(key));
        }
        resultMap.put("text", "个人资料统计（柱状图）");
        resultMap.put("subText", "统计维度：资料类型");
        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);
        return Result.success(resultMap);
    }
}

