package com.honomoly.garbages.mapper;

import java.util.List;

import com.honomoly.garbages.entity.ChatEntity;

public interface ChatMapper extends AbstractMapper<ChatEntity> {

    List<ChatEntity> selectByUsersId(long usersId);

}
