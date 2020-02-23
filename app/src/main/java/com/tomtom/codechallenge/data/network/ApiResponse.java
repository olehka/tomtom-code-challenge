package com.tomtom.codechallenge.data.network;

import androidx.annotation.Nullable;

final class ApiResponse<T> {

    private final @Nullable T body;
    private final @Nullable String error;

    private ApiResponse(@Nullable T body, @Nullable String error) {
        this.body = body;
        this.error = error;
    }

    static <T> ApiResponse<T> success(@Nullable T body) {
        return new ApiResponse<>(body, null);
    }

    static <T> ApiResponse<T> error(@Nullable String error) {
        return new ApiResponse<>(null, error);
    }
}
