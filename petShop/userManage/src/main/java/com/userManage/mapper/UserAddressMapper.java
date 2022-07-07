package com.userManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserAddressMapper extends BaseMapper<UserAddress> {
}
