package com.example.controller;

import com.example.common.Result;
import com.example.entity.Collect;
import com.example.entity.Menu;
import com.example.entity.User;
import com.example.entity.UserArticle;
import com.example.service.CollectService;
import com.example.service.MenuService;
import com.example.service.UserArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: MenuController
 * Package: com.example.controller
 * Description:
 *
 * @Author Hxy
 * @Create 2024/5/3 20:52
 * @Version 1.0
 */
@RestController
public class MenuController {
    @Resource
    private MenuService menuService;
    @Resource
    private UserArticleService userArticleService;
    @GetMapping("/menu/selectAll")
    Result SelectAll(Menu menu){
       List<Menu> list =menuService.selectAll(menu);
       return Result.success(list);
    }
    @GetMapping("/menu/Addmenu")
    Result Addmenu(Menu menu){
        menuService.Addmenu(menu);
        return Result.success();
    }
    @GetMapping("/menu/Delmenu")
    Result Delmenu(Menu menu){
        UserArticle userArticle =new UserArticle();
        userArticle.setName(menu.getName());
        userArticle.setcId(menu.getUserId());
        userArticleService.deletebyName(userArticle);
        menuService.Delmenu(menu);
        return Result.success();
    }
}
