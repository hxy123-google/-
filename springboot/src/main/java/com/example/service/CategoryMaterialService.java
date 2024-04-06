package com.example.service;

import com.example.entity.CategoryMaterial;
import com.example.mapper.CategoryMaterialMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: CategoryMaterialService
 * Package: com.example.service
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/7 0:25
 * @Version 1.0
 */
@Service
public class CategoryMaterialService {
    @Resource
    private CategoryMaterialMapper categoryMaterialMapper;
    public List<CategoryMaterial> select(){
        return categoryMaterialMapper.select();
    }

}
