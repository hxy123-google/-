package com.example.mapper;

import com.example.entity.Scoreorder;

import java.util.List;

/**
 * ClassName: ScoreorderMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/11 16:05
 * @Version 1.0
 */
public interface ScoreorderMapper {
    int insert(Scoreorder scoreorder);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Scoreorder scoreorder);

    /**
     * 根据ID查询
     */
    Scoreorder selectById(Integer id);

    /**
     * 查询所有
     */
    List<Scoreorder> selectAll(Scoreorder scoreorder);
}
