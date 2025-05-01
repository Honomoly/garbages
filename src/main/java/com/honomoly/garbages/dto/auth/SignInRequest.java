package com.honomoly.garbages.dto.auth;

import jakarta.validation.constraints.NotNull;

public record SignInRequest(
    @NotNull String identifier,
    @NotNull String password
) {}
