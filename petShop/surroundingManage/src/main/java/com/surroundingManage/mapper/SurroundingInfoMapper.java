package com.surroundingManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.entity.SurroundingInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SurroundingInfoMapper extends BaseMapper<SurroundingInfo> {
}
