package com.example.mapper;

import com.example.entity.CategoryMaterial;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: MaterialMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/7 0:24
 * @Version 1.0
 */
public interface CategoryMaterialMapper {
    @Select("select * from categorymaterial")
    List<CategoryMaterial> select();
}
