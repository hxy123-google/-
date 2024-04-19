package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Article;
import com.example.entity.ArticleScore;
import com.example.entity.ArticleScoreParam;
import com.example.entity.Score;
import com.example.exception.CustomException;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleScoreService;
import com.example.service.ArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: ArticleScoreController
 * Package: com.example.controller
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/19 1:32
 * @Version 1.0
 */
@RestController
public class ArticleScoreController {
    @Resource
    private ArticleScoreService articleScoreService;
    @Resource
    private ArticleMapper articleMapper;
    @GetMapping("/articlescore/")
    public Result add(ArticleScore articleScore){
        Article article=articleMapper.selectById(articleScore.getArticleId());
        if (ObjectUtil.isNull(article)) {
            throw new CustomException(ResultCodeEnum.CITE_NOT_EXIST);
        }
        ArticleScore articleScore1=articleScoreService.selectId(articleScore);
        if (!ObjectUtil.isNull(articleScore1)) {
            throw new CustomException(ResultCodeEnum.CITE_NOT_EXIST);
        }
        articleScoreService.add(articleScore);
        return  Result.success();
    }
    @GetMapping("/articlescore/selectScore/")
    public Result selectScore(ArticleScore articleScore,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<Score> page=articleScoreService.selectScore(articleScore,pageNum,pageSize);
        return Result.success(page);
    }
    @GetMapping("/articlescore/selectArticle/")
    public Result selectArticle(ArticleScore articleScore,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<Article> page=articleScoreService.selectArticle(articleScore,pageNum,pageSize);
        return Result.success(page);
    }
}
