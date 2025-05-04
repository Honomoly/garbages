package com.honomoly.garbages.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.honomoly.garbages.entity.ChatEntity;

@Mapper
public interface ChatMapper extends AbstractMapper<ChatEntity> {

    List<ChatEntity> selectByUsersId(long usersId);

}
