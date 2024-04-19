package com.example.mapper;

import com.example.entity.Article;
import com.example.entity.ArticleScore;
import com.example.entity.Score;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: ArticleScoreMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/19 1:33
 * @Version 1.0
 */
public interface ArticleScoreMapper {
    public void insert(ArticleScore article);
    List<ArticleScore> selectAll(ArticleScore article);
    @Select("select * from articlescore where article_id=#{articleId} and score_id=#{scoreId}")
    ArticleScore selectId(ArticleScore article);

    List<Score> selectScore(ArticleScore articleScore);

    List<Article> selectArticle(ArticleScore articleScore);
}
