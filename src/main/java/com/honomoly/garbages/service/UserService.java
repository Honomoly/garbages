package com.honomoly.garbages.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honomoly.garbages.entity.UserEntity;
import com.honomoly.garbages.mapper.UserMapper;

@Service
public class UserService extends AbstractService<UserEntity> {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected UserMapper getMapper() {
        return userMapper;
    }

    public UserEntity selectByIdentifier(String identifier) {
        return getMapper().selectByIdentifier(identifier);
    }
}
