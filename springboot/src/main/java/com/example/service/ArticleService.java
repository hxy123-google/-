package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.RecommendEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.Article;
import com.example.entity.Orders;
import com.example.exception.CustomException;
import com.example.mapper.ArticleMapper;
import com.example.mapper.OrdersMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * ClassName: ArticleService
 * Package: com.example.service
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/4 16:46
 * @Version 1.0
 */
@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private OrdersMapper ordersMapper;
    /**
     * 新增
     */
    public void add(Article article) {
        System.out.println(article);
        if (ObjectUtil.isNotEmpty(article.getRecommend()) && RecommendEnum.YES.status.equals(article.getRecommend())) {
            // 做一下校验
            Article recommend = articleMapper.getRecommend(article.getType());
            if (ObjectUtil.isNotEmpty(recommend)) {
                throw new CustomException(ResultCodeEnum.RECOMMEND_ALREADY_ERROR);
            }
        }
        System.out.println(article);
        articleMapper.insert(article);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            articleMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Article article) {
        if (ObjectUtil.isNotEmpty(article.getRecommend()) && RecommendEnum.YES.status.equals(article.getRecommend())) {
            // 做一下校验
            Article recommend = articleMapper.getRecommend(article.getType());
            if (ObjectUtil.isNotEmpty(recommend) && !recommend.getId().equals(article.getId())) {
                throw new CustomException(ResultCodeEnum.RECOMMEND_ALREADY_ERROR);
            }
        }
        articleMapper.updateById(article);
    }

    /**
     * 根据ID查询
     */
    public Article selectById(Integer id) {
        Article course = articleMapper.selectById(id);
        // 把敏感数据干掉
        Account currentUser = TokenUtils.getCurrentUser();
        Orders orders = new Orders();
        orders.setUserId(currentUser.getId());
        orders.setArticleId(id);
        List<Orders> ordersList = ordersMapper.selectAll(orders);
        if (ObjectUtil.isEmpty(ordersList)) {
            course.setFile("");
            course.setVideo("");
        }
        return course;

    }

    /**
     * 查询所有
     */
    public List<Article> selectAll(Article article) {
        return articleMapper.selectAll(null, null, article.getId(), article.getName(), article.getType(),
                article.getRecommend(),article.getCategory(),article.getAuthor());
    }

    /**
     * 分页查询
     */
    public PageInfo<Article> selectPage(Article article, Integer pageNum, Integer pageSize, Date startdate, Date enddate) {
        PageHelper.startPage(pageNum, pageSize);
        System.out.println(article);
        System.out.println(startdate);
        List<Article> list = articleMapper.selectAll(startdate, enddate, article.getId(), article.getName(),
                article.getType(),article.getRecommend(),article.getCategory(),article.getAuthor());

        return PageInfo.of(list);
    }
    public PageInfo<Article> selectPager(Article article, Integer pageNum, Integer pageSize, Date startdate, Date enddate) {
        PageHelper.startPage(pageNum, pageSize);
        System.out.println(article);
        System.out.println(startdate);
        List<Article> list = articleMapper.selectAllr(startdate, enddate, article.getId(), article.getName(),
                article.getType(),article.getRecommend(),article.getCategory(),article.getAuthor());

        return PageInfo.of(list);
    }

    public Article getRecommend(String type) {
        return articleMapper.getRecommend(type);
    }

    public List<Article> selectTop8(String type) {
        return articleMapper.selectTop8(type);
    }

    public PageInfo<Article> selectAccPage(Article article, Integer pageNum, Integer pageSize, Date startdate, Date enddate) {
        PageHelper.startPage(pageNum, pageSize);
        System.out.println(article);
        System.out.println(startdate);
        List<Article> list = articleMapper.selectAcc(startdate, enddate,article.getId(),article.getName(),article.getAuthor(),article.getJournal(),article.getCategory());
        return PageInfo.of(list);
    }
}
