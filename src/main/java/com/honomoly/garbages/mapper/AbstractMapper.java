package com.honomoly.garbages.mapper;

import java.util.List;

import com.honomoly.garbages.entity.AbstractEntity;

/**
 * Mapper가 기본적으로 가지는 CRUD
 * 자세한 로직은 xml에서 작성
 */
public interface AbstractMapper<T extends AbstractEntity> {

    /** 단일 삽입 */
    void insert(T entity);

    /** 단일 선택 */
    T selectById(long id);

    /** 다중 선택 (start보다 작은 limit개의 데이터) */
    List<T> select(long start, long limit);

    /** 단일 업데이트 */
    void update(T entity);

    /** 단일 삭제 */
    void deleteById(long id);

}
