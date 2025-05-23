package com.honomoly.garbages.service;

import java.util.List;

import com.honomoly.garbages.entity.AbstractEntity;
import com.honomoly.garbages.mapper.AbstractMapper;

public abstract class AbstractService<T extends AbstractEntity> {

    /** 상속 클래스에서 Mapper을 주입하여 반환 */
    protected abstract AbstractMapper<T> getMapper();

    /**
     * 해당 entity 값 삽입후, 삽입 결과 데이터를 가져온다
     */
    public void insert(T entity) {
        getMapper().insert(entity);
        entity.copyFrom(getMapper().selectById(entity.getId()));
    }

    public T selectById(long id) {
        return getMapper().selectById(id);
    }

    public List<T> select(long start, long limit) {
        return getMapper().select(start, limit);
    }

    public void update(T entity) {
        getMapper().update(entity);
    }

    public void deleteById(long id) {
        getMapper().deleteById(id);
    }

}
