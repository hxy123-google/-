package com.example.mapper;

import com.example.entity.UserArticle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: UserArticleMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/5/5 18:44
 * @Version 1.0
 */
public interface UserArticleMapper {
    void add(UserArticle userArticle);

    List<UserArticle> selectAll(UserArticle userArticle);
    @Delete("delete from userarticle where id=#{id}")
    void deletebyId(UserArticle userArticle);
    @Select("select * from userarticle where id=#{id}")
    UserArticle selectbyId(Integer id);

    void edit(UserArticle userArticle);
    @Delete("delete from userarticle where c_id=#{cId} and name=#{name}")
    void deleteByName(UserArticle userArticle);
}
