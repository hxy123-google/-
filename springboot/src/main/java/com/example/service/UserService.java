package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.MemberEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomException;
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
public class UserService {

    @Resource
    private UserMapper userMapper;

    public Account login(Account account) {
        Account dbUser = userMapper.selectByUserName(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        if(!account.getRole().equals(dbUser.getRole())){
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData;
        if(dbUser.getRole()=="USER")  tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        else tokenData=dbUser.getId()+"-"+RoleEnum.PRO.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public void add(User user) {
        // 判断一下该用户在数据库里有没有
        User dbUser = userMapper.selectByUserName(user.getUsername());
        if (ObjectUtil.isNotEmpty(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        // 初始化一些页面上没有填写的信息
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setMember(MemberEnum.NO.info);
        //user.setRole();
        userMapper.insert(user);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(User user) {

        userMapper.updateById(user);
    }

    /**
     * 根据ID查询
     */
    public User selectById(Integer id) {

        User user= userMapper.selectById(id);
        String tokenData;
        if(user.getRole()=="USER")  tokenData = user.getId() + "-" + RoleEnum.USER.name();
        else tokenData=user.getId()+"-"+RoleEnum.PRO.name();
        String token = TokenUtils.createToken(tokenData, user.getPassword());
        user.setToken(token);
        return user;
    }

    /**
     * 查询所有
     */
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    /**
     * 分页查询
     */
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    public void register(Account account) {
        User user = new User();
        BeanUtils.copyProperties(account, user);
        add(user);
    }

    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUserName(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbUser.setPassword(account.getNewPassword());
        userMapper.updateById(dbUser);
    }

    public PageInfo<User> selectPro(User user,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectPro(user);
        return PageInfo.of(list);
    }
    public void recharge(Double account) {
        Account currentUser = TokenUtils.getCurrentUser();
        User user = userMapper.selectById(currentUser.getId());

        user.setAccount(user.getAccount() + account);

        user.setScore(user.getScore()+(int) (account*10));
        // 是否充值一次性满500
        if (account >= 50) {
            user.setMember(MemberEnum.YES.info);
        }
        userMapper.updateById(user);
    }

    public List<User> selectTop8(String role) {
        List<User> list=userMapper.selectTop8(role);
        return list;
    }

    public void updateByUsername(User user) {
        User user1=userMapper.selectByUserName(user.getUsername());
        user1.setScore(user1.getScore()+10);
        userMapper.updateByUsername(user1);
    }

    public PageInfo<User> getAccPro(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.getAccPro(user);
        return PageInfo.of(list);
    }
}
