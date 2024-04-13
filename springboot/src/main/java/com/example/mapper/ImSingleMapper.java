package com.example.mapper;

import com.example.entity.ImSingle;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: ImSIngleMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/13 10:24
 * @Version 1.0
 */
public interface ImSingleMapper  {
    @Select("select * from imsingle where (fromuser = #{fromUser} and touser = #{toUser}) or (fromuser = #{toUser} and touser = #{fromUser})")
    List<ImSingle> findByUsername(String fromUser, String toUser);

    @Select("select * from imsingle where touser = #{toUser} and readed = 0")
    List<ImSingle> findByToUsername(String toUser);

    void insertSelective(ImSingle imSingle);
}
