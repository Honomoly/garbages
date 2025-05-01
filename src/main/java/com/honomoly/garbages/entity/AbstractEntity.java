package com.honomoly.garbages.entity;

import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;

/** 모든 DB 데이터가 기본적으로 가지는 필드 */
@Getter
@Setter
public abstract class AbstractEntity {

    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public void copyFrom(Object other) {
        BeanUtils.copyProperties(other, this);
    }

}
