package com.honomoly.garbages.dto.auth;

import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
    @NotNull String identifier,
    @NotNull String nickname,
    @NotNull String password
) {}
