package com.example.service;

import com.example.entity.Article;
import com.example.entity.ArticleScore;
import com.example.entity.Score;
import com.example.mapper.ArticleScoreMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: ArticleScoreService
 * Package: com.example.service
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/19 1:33
 * @Version 1.0
 */
@Service
public class ArticleScoreService {
    @Resource
    private ArticleScoreMapper articleScoreMapper;
    public void add(ArticleScore articleScore) {
        articleScoreMapper.insert(articleScore);
    }
    public ArticleScore selectId(ArticleScore articleScore){
        return articleScoreMapper.selectId(articleScore);
    }
    public PageInfo<Score> selectScore(ArticleScore articleScore,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Score>list=articleScoreMapper.selectScore(articleScore);
        return PageInfo.of(list);
    }

    public PageInfo<Article> selectArticle(ArticleScore articleScore, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article>list=articleScoreMapper.selectArticle(articleScore);
        return PageInfo.of(list);
    }
}
