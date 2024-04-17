package com.example.mapper;

import com.example.entity.Orders;

import java.util.List;

/**
 * ClassName: OrdersMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/16 9:46
 * @Version 1.0
public interface OrdersMapper {

/**
 * 新增
 */
public interface OrdersMapper {

    /**
     * 新增
     */
    int insert(Orders orders);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Orders orders);

    /**
     * 根据ID查询
     */
    Orders selectById(Integer id);

    /**
     * 查询所有
     */
    List<Orders> selectAll(Orders orders);

}