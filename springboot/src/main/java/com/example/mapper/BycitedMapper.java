package com.example.mapper;

import com.example.entity.Article;
import com.example.entity.Bycited;

import java.util.Date;
import java.util.List;

/**
 * ClassName: BycitedMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/18 15:30
 * @Version 1.0
 */
public interface BycitedMapper {
    int insert(Bycited bycited);
    List<Bycited> selectAll(Bycited bycited);

    List<Article> findById(Bycited bycited);
}
