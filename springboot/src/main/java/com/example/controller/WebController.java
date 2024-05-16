package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Article;
import com.example.service.AdminService;
import com.example.service.ArticleService;
import com.example.service.ProfessorService;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础前端接口
 */
@RestController
public class WebController {
    @Resource
    private ArticleService articleService;
    @Resource
    private AdminService adminService;
    @Resource
    private UserService userService;
    @Resource
    private ProfessorService professorService;
    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            account = adminService.login(account);
        }
        if(RoleEnum.USER.name().equals(account.getRole())){
            account= userService.login(account);
            System.out.println("hi");
        }
        if(RoleEnum.PRO.name().equals(account.getRole())){
            account=userService.login(account);
            //account= professorService.login(account);
        }
        return Result.success(account);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.register(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.register(account);
        }
        if (RoleEnum.PRO.name().equals(account.getRole())) {
            //account=userService.login(account);
            userService.register(account);
        }
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.updatePassword(account);
        }
        if (RoleEnum.PRO.name().equals(account.getRole())) {
            userService.updatePassword(account);
            //professorService.updatePassword(account);
        }
        return Result.success();
    }
    @GetMapping("/getBar")
    public Result getBar() {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> xList = new ArrayList<>();
        List<Long> yList = new ArrayList<>();

        xList.add("复合材料");
        xList.add("石墨烯");
        xList.add("冶金学");
        xList.add("碳纳米管");
        xList.add("TiO_2");
        xList.add("建筑材料");

        List<Article> courses = articleService.selectAll(new Article());
        yList.add(courses.stream().filter(x -> "复合材料".equals(x.getCategory())).count());
        yList.add(courses.stream().filter(x -> "石墨烯".equals(x.getCategory())).count());
        yList.add(courses.stream().filter(x -> "冶金学".equals(x.getCategory())).count());
        yList.add(courses.stream().filter(x -> "碳纳米管".equals(x.getCategory())).count());
        yList.add(courses.stream().filter(x -> "TiO_2".equals(x.getCategory())).count());
        yList.add(courses.stream().filter(x -> "建筑材料".equals(x.getCategory())).count());
        resultMap.put("text", "平台所有资料总数统计（柱状图）");
        resultMap.put("subText", "统计维度：资料类型");
        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);
        return Result.success(resultMap);
    }

}
