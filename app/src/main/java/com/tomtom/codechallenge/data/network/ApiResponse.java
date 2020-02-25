package com.tomtom.codechallenge.data.network;

import android.text.TextUtils;

import androidx.annotation.Nullable;

public final class ApiResponse<T> {

    private final @Nullable T body;
    private final @Nullable String error;

    private ApiResponse(@Nullable T body, @Nullable String error) {
        this.body = body;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(@Nullable T body) {
        return new ApiResponse<>(body, null);
    }

    public static <T> ApiResponse<T> error(@Nullable String error) {
        return new ApiResponse<>(null, error);
    }

    public boolean hasError() {
        return error != null && error.length() != 0;
    }

    @Nullable
    public String getError() {
        return error;
    }

    @Nullable
    public T getBody() {
        return body;
    }
}
