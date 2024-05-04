package com.example.mapper;

import com.example.entity.Article;
import com.example.entity.Collect;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: CollectionMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/18 19:16
 * @Version 1.0
 */
public interface CollectMapper {
    int insert(Collect collection);
    List<Collect> selectAll(Collect collection);
    @Select("select * from collection where article_id=#{articleId} and c_id=#{cId}")
    Collect selectById(Collect collection);
    List<Article> findById(Integer cId,String category,String journal,
                           String type,String keywords,String aName,
                           String author);
    int deleteById(Collect collect);

    void deleteByName(Collect collection);
}
