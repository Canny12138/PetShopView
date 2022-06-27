package com.petManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.entity.PetInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/27 15:12
 **/
@Repository
@Mapper
public interface PetInfoMapper extends BaseMapper<PetInfo> {

}
