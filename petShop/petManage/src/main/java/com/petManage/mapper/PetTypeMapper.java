package com.petManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.entity.PetType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PetTypeMapper extends BaseMapper<PetType> {

}
