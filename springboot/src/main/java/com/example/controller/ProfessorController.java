package com.example.controller;

import com.example.common.Result;
import com.example.entity.Professor;
import com.example.entity.User;
import com.example.service.ProfessorService;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: UserController
 * Package: com.example.controller
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/4 0:52
 * @Version 1.0
 */
@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Resource
    private ProfessorService professorService;

    @PostMapping("/add")
    public Result add(@RequestBody Professor user) {
        professorService.add(user);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        professorService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        professorService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Professor user) {
        professorService.updateById(user);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Professor user = professorService.selectById(id);
        return Result.success(user);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Professor user ) {
        List<Professor> list = professorService.selectAll(user);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Professor user,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Professor> page = professorService.selectPage(user, pageNum, pageSize);
        return Result.success(page);
    }
}