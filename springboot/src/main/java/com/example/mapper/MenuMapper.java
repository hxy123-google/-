package com.example.mapper;

import com.example.entity.Menu;
import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: MenuMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/5/3 20:54
 * @Version 1.0
 */

public interface MenuMapper {
    @Select("select * from menu where user_id=#{userId}")
    public List<Menu> selectAll(Menu menu);

    void Addmenu(Menu menu);
}
