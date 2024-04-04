package com.example.controller;

import com.example.common.Result;
import com.example.entity.Article;
import com.example.service.ArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
            Article article = articleService.selectById(id);
            return Result.success(article);
        }

        /**
         * 查询所有
         */
        @GetMapping("/selectAll")
        public Result selectAll(Article article ) {
            List<Article> list = articleService.selectAll(article);
            return Result.success(list);
        }

        /**
         * 分页查询
         */
        @GetMapping("/selectPage")
        public Result selectPage(Article article,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
            PageInfo<Article> page = articleService.selectPage(article, pageNum, pageSize);
            return Result.success(page);
        }

    
}
