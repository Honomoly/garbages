package com.honomoly.garbages.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.honomoly.garbages.entity.UserEntity;

import jakarta.validation.constraints.NotNull;

/** 유저 정보중 필요한 정보만 추출한 DTO */
public record UserDto(
    @NotNull Long id,
    @NotNull String identifier,
    @NotNull String nickname,
    @NotNull @JsonProperty("created_at") Instant createdAt,
    @NotNull @JsonProperty("updated_at") Instant updatedAt
) {
    public UserDto(UserEntity user) {
        this(
            user.getId(),
            user.getIdentifier(),
            user.getNickname(),
            user.getCreatedAt().toInstant(),
            user.getUpdatedAt().toInstant()
        );
    }
}
