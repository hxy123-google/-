package com.example.mapper;

import com.example.entity.Professor;
import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: UserMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/4 0:50
 * @Version 1.0
 */
public interface ProfessorMapper {
    @Select("select * from professor where username = #{username}")
    Professor selectByUserName(String username);

    void insert(Professor user);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Professor user);

    /**
     * 根据ID查询
     */
    Professor selectById(Integer id);

    /**
     * 查询所有
     */
    List<Professor> selectAll(Professor user);
}
