package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.Article;
import com.example.entity.User;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        userService.add(user);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        userService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @GetMapping("/updateByUsername")
    public Result updateByUsername( User user) {
        userService.updateByUsername(user);
        return Result.success();
    }
    @PutMapping("/update")
    public Result updateById(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(User user ) {
        List<User> list = userService.selectAll(user);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(User user,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<User> page = userService.selectPage(user, pageNum, pageSize);
        return Result.success(page);
    }
    @GetMapping("/selectPro")
    public Result selectPro(User user,
                            @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<User> page = userService.selectPro( user,pageNum, pageSize);
        return Result.success(page);
    }
    @GetMapping("/recharge")
    public Result recharge(@RequestParam Double account) {
        userService.recharge(account);
        return Result.success();
    }
    @GetMapping("/selectTop8")
    public Result selectTop8(@RequestParam String role) {
        List<User> list = userService.selectTop8(role);
        return Result.success(list);
    }
    @GetMapping("/getAccPro")
    public Result getAccPro(User user,
                            @RequestParam(defaultValue = "1") Integer pageNum1,
                            @RequestParam(defaultValue = "1") Integer pageSize1){
        PageInfo<User> page = userService.getAccPro(user,pageNum1,pageSize1);
        return Result.success(page);

    }
    @GetMapping("/getPie")
    public Result getPie() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        List<User> ordersList = userService.selectAll(new User());
        Map<String, Long> collect = ordersList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getRole()))
                .collect(Collectors.groupingBy(User::getRole, Collectors.counting()));
        for (String key : collect.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            map.put("value", collect.get(key));
            list.add(map);
        }

        resultMap.put("text", "平台教授占比统计（饼图）");
        resultMap.put("subText", "统计维度：是否为教授身份");
        resultMap.put("name", "占比数据");
        resultMap.put("data", list);

        return Result.success(resultMap);
    }
}