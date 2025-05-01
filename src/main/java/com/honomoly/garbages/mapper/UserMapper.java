package com.honomoly.garbages.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.honomoly.garbages.entity.UserEntity;

@Mapper
public interface UserMapper extends AbstractMapper<UserEntity> {

    UserEntity selectByIdentifier(String identifier);

}
