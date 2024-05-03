package com.example.service;

import com.example.entity.Menu;
import com.example.entity.User;
import com.example.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: MenuService
 * Package: com.example.service
 * Description:
 *
 * @Author Hxy
 * @Create 2024/5/3 20:54
 * @Version 1.0
 */
@Service
public class MenuService {
    @Resource
    private MenuMapper menuMapper;
    public List<Menu> selectAll(Menu menu) {
        List<Menu> list=menuMapper.selectAll(menu);
        return list;
    }

    public void Addmenu(Menu menu) {
        menuMapper.Addmenu(menu);
    }
}
