package com.example.controller;

import com.example.common.Result;
import com.example.entity.Article;
import com.example.entity.Orders;
import com.example.entity.UserArticle;
import com.example.service.ArticleService;
import com.example.service.OrdersService;
import com.example.service.UserArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

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
            //List<Orders> list=ordersService.selectAll(orders);
            Article article = articleService.selectById(userArticle.getArticleId());
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
}

