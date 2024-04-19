package com.example.mapper;

import com.example.entity.Article;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
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
        List<Article> selectAll(Date startdate, Date enddate, Integer id, String name, String type,String recommend,
                                String category,String author,String journal);
        @Select("select * from article where author_id=#{authorId}")
        List<Article> selectByAuthorId(Integer authorId);

        @Select("select * from article where recommend = '是'and type = #{type}")
        Article getRecommend(String type);
        @Select("select * from article where recommend = '否' and type=#{type} order by id desc limit 8")
        List<Article> selectTop8(String type);

        List<Article> selectAcc(Date startdate, Date enddate, Integer id,String name,String author, String journal,String category);

        List<Article> selectAllr( Date startdate, Date enddate, Integer id, String name, String type,String recommend,
                                 String category,String author,Integer authorId,String journal);

        List<Article> selectArticle(Article article);
}
