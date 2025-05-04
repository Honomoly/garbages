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
    private Byte status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public void copyFrom(Object other) {
        BeanUtils.copyProperties(other, this);
    }

    @Override
    public int hashCode() {
        if (id != null)
            return id.intValue();
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractEntity ett)
            return ett.getClass() == this.getClass() && ett.getId() != null && ett.getId().equals(this.id);
        return false;
    }

}
