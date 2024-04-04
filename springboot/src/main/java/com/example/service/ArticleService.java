package com.example.service;

import com.example.entity.Article;
import com.example.mapper.ArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
            return articleMapper.selectAll(article);
        }

        /**
         * 分页查询
         */
        public PageInfo<Article> selectPage(Article article, Integer pageNum, Integer pageSize) {
            PageHelper.startPage(pageNum, pageSize);
            System.out.println(article);
            List<Article> list = articleMapper.selectAll(article);
            System.out.println(article);
            return PageInfo.of(list);
        }


}
