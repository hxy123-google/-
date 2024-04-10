package com.example.mapper;

import com.example.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: CommentMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/9 16:46
 * @Version 1.0
 */
public interface CommentMapper {
    /**
     * 新增
     */
    int insert(Comment comment);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Comment comment);

    /**
     * 根据ID查询
     */
    Comment selectById(Integer id);

    /**
     * 查询所有
     */
    List<Comment> selectAll(Comment comment);

    List<Comment> selectForUser(Comment comment);
    @Select("select count(*) from comment where fid = #{fid} and module = #{module}")
    Integer selectCount(@Param("fid") Integer fid, @Param("module") String module);
}
