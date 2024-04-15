package com.example.mapper;

import com.example.entity.User;
import com.github.pagehelper.PageInfo;
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
public interface UserMapper {
    @Select("select * from user where username = #{username} ")
    User selectByUserName(String username);

    void insert(User user);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(User user);
    int updateByUsername(User user);
    /**
     * 根据ID查询
     */
    User selectById(Integer id);

    /**
     * 查询所有
     */
    List<User> selectAll(User user);

    @Select("select * from user where role='PRO'")
    List<User> selectPro();
    @Select("select * from user where role=#{role} order by id desc limit 8 ")
    List<User> selectTop8(String role);
}
