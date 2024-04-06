package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.MemberEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Professor;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.ProfessorMapper;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: UserService
 * Package: com.example.service
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/4 0:53
 * @Version 1.0
 */
@Service
public class ProfessorService {

    @Resource
    private ProfessorMapper professorMapper;
    @Resource
    private UserMapper userMapper;
    public Account login(Account account) {
        Account dbUser = professorMapper.selectByUserName(account.getUsername());
        System.out.println(dbUser);
        //User user1=userMapper.selectByUserName(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.PRO.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public void add(Professor user) {
        // 判断一下该用户在数据库里有没有
        Professor dbUser = professorMapper.selectByUserName(user.getUsername());
        User user1=userMapper.selectByUserName(user.getUsername());
        if (ObjectUtil.isNotEmpty(dbUser)||ObjectUtil.isNotEmpty(user1)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        // 初始化一些页面上没有填写的信息
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setMember(MemberEnum.YES.info);
        user.setRole(RoleEnum.PRO.name());
        professorMapper.insert(user);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        professorMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            professorMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Professor user) {
        professorMapper.updateById(user);
    }

    /**
     * 根据ID查询
     */
    public Professor selectById(Integer id) {
        return professorMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Professor> selectAll(Professor user) {
        return professorMapper.selectAll(user);
    }

    /**
     * 分页查询
     */
    public PageInfo<Professor> selectPage(Professor user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Professor> list = professorMapper.selectAll(user);
        return PageInfo.of(list);
    }

    public void register(Account account) {
        Professor user = new Professor();
        BeanUtils.copyProperties(account, user);
        add(user);
    }

    public void updatePassword(Account account) {
        Professor dbUser = professorMapper.selectByUserName(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbUser.setPassword(account.getNewPassword());
        professorMapper.updateById(dbUser);
    }
}
