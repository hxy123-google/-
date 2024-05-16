package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.common.enums.RecommendEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Article;
import com.example.entity.Bycited;
import com.example.exception.CustomException;
import com.example.mapper.ArticleMapper;
import com.example.mapper.BycitedMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ClassName: BycitedService
 * Package: com.example.service
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/18 15:30
 * @Version 1.0
 */
@Service
public class BycitedService {
    @Resource
    private BycitedMapper bycitedMapper;
    @Resource
    private ArticleMapper articleMapper;
    public void add(Bycited bycited) {
        Article articlee=articleMapper.selectById(bycited.getCiteId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        bycited.setTime(sdf.format(articlee.getTime()));
        String year= DateUtil.format(articlee.getTime(), "yyyy");
        bycited.setYear(year);
        bycitedMapper.insert(bycited);
    }


    public PageInfo<Article> selectPager(Bycited bycited, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> list = bycitedMapper.findById(bycited);
        return PageInfo.of(list);
    }

    public List<Bycited> selectAll(Bycited bycited) {
        return bycitedMapper.selectAll(bycited);
    }
}
