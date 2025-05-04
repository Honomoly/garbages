package com.honomoly.garbages.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatEntity extends AbstractEntity {

    private Long usersId;
    private String message;

}
