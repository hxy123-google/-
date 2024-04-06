package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.RecommendEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Article;
import com.example.exception.CustomException;
import com.example.mapper.ArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * ClassName: ArticleService
 * Package: com.example.service
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/4 16:46
 * @Version 1.0
 */
@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 新增
     */
    public void add(Article article) {
        System.out.println(article);
        if (ObjectUtil.isNotEmpty(article.getRecommend()) && RecommendEnum.YES.status.equals(article.getRecommend())) {
            // 做一下校验
            Article recommend = articleMapper.getRecommend();
            if (ObjectUtil.isNotEmpty(recommend)) {
                throw new CustomException(ResultCodeEnum.RECOMMEND_ALREADY_ERROR);
            }
        }
        articleMapper.insert(article);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            articleMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Article article) {
        if (ObjectUtil.isNotEmpty(article.getRecommend()) && RecommendEnum.YES.status.equals(article.getRecommend())) {
            // 做一下校验
            Article recommend = articleMapper.getRecommend();
            if (ObjectUtil.isNotEmpty(recommend) && !recommend.getId().equals(article.getId())) {
                throw new CustomException(ResultCodeEnum.RECOMMEND_ALREADY_ERROR);
            }
        }
        articleMapper.updateById(article);
    }

    /**
     * 根据ID查询
     */
    public Article selectById(Integer id) {
        return articleMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Article> selectAll(Article article) {
        return articleMapper.selectAll(null, null, article.getId(), article.getName(), article.getType(),
                article.getRecommend());
    }

    /**
     * 分页查询
     */
    public PageInfo<Article> selectPage(Article article, Integer pageNum, Integer pageSize, Date startdate, Date enddate) {
        PageHelper.startPage(pageNum, pageSize);
        System.out.println(article);
        System.out.println(startdate);
        List<Article> list = articleMapper.selectAll(startdate, enddate, article.getId(), article.getName(),
                article.getType(),article.getRecommend());

        return PageInfo.of(list);
    }

    public Article getRecommend() {
        return articleMapper.getRecommend();
    }

    public List<Article> selectTop8() {
        return articleMapper.selectTop8();
    }
}
