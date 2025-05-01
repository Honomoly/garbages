package com.honomoly.garbages.mapper;

import java.util.List;

import com.honomoly.garbages.entity.AbstractEntity;

/**
 * Mapper가 기본적으로 가지는 CRUD
 * 자세한 로직은 xml에서 작성
 */
public interface AbstractMapper<T extends AbstractEntity> {
    void insert(T entity);
    T selectById(long id);
    List<T> select(long start, long limit);
    void update(T entity);
    void deleteById(long id);
}
