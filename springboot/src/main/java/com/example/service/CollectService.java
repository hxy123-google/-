package com.example.service;

import com.example.entity.Article;
import com.example.entity.Bycited;
import com.example.entity.Collect;
import com.example.mapper.CollectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: ControllerService
 * Package: com.example.service
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/18 19:15
 * @Version 1.0
 */
@Service
public class CollectService {
    @Resource
    private CollectMapper collectMapper;

    public PageInfo<Article> selectPager(Collect collect, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> list = collectMapper.findById(collect);
        return PageInfo.of(list);
    }

    public Collect selectById(Collect collection) {
       return collectMapper.selectById(collection);
    }

    public void add(Collect collection) {
        collectMapper.insert(collection);
    }
    public void deletbyId(Collect collection){
        collectMapper.deleteById(collection);
    }
}
