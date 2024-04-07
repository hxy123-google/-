package com.example.mapper;

import com.example.entity.Score;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: ScoreMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/8 0:19
 * @Version 1.0
 */
public interface ScoreMapper {
    /**
     * 新增
     */
    int insert(Score score);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Score score);

    /**
     * 根据ID查询
     */
    Score selectById(Integer id);

    /**
     * 查询所有
     */
    List<Score> selectAll(Score score);

    @Select("select * from score where recommend = '是'")
    Score selectRecommend();
}
