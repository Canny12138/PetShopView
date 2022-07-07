package com.userManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
