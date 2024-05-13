package com.example.service;

import com.example.entity.Article;
import com.example.entity.UserArticle;
import com.example.mapper.UserArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: UserArticleService
 * Package: com.example.service
 * Description:
 *
 * @Author Hxy
 * @Create 2024/5/5 18:44
 * @Version 1.0
 */
@Service
public class UserArticleService {
    @Resource
    private UserArticleMapper userArticleMapper;
    public void add(UserArticle userArticle) {
        userArticleMapper.add(userArticle);
    }

    public PageInfo<UserArticle> selectPager(UserArticle userArticle, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserArticle> list = userArticleMapper.selectAll(userArticle);
        return PageInfo.of(list);
    }

    public void delete(UserArticle userArticle) {
        userArticleMapper.deletebyId(userArticle);
    }

    public UserArticle selectById(Integer id) {
        UserArticle userArticle=userArticleMapper.selectbyId(id);
        return userArticle;
    }

    public void edit(UserArticle userArticle) {
        userArticleMapper.edit(userArticle);
    }

    public void deletebyName(UserArticle userArticle) {
        userArticleMapper.deleteByName(userArticle);
    }
}
