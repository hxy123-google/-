package com.example.mapper;

import com.example.entity.Article;

import java.util.List;

/**
 * ClassName: ArticleMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/4 16:42
 * @Version 1.0
 */
public interface ArticleMapper {


        /**
         * 新增
         */
        int insert(Article article);

        /**
         * 删除
         */
        int deleteById(Integer id);

        /**
         * 修改
         */
        int updateById(Article Article);

        /**
         * 根据ID查询
         */
        Article selectById(Integer id);

        /**
         * 查询所有
         */
        List<Article> selectAll(Article article);


}
