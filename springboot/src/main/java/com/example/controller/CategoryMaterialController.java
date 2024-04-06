package com.example.controller;

import com.example.common.Result;
import com.example.entity.CategoryMaterial;
import com.example.service.CategoryMaterialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: MaterialController
 * Package: com.example.controller
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/7 0:23
 * @Version 1.0
 */
@RestController
public class CategoryMaterialController {
    @Resource
    private CategoryMaterialService categoryMaterialService;
    @GetMapping("/category")
    public Result select(){
        List<CategoryMaterial> list=categoryMaterialService.select();
        return Result.success(list);
    }
}
