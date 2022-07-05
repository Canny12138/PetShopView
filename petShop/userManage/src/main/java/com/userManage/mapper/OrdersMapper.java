package com.userManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
