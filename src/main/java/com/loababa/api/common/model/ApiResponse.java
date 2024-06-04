package com.loababa.api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiResponse<T>(
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data
) {

    public static ApiResponse<Void> success() {
        return new ApiResponse<>("OK", null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("OK", data);
    }

    public static ApiResponse<Void> fail(String clientErrorMessage) {
        return new ApiResponse<>(clientErrorMessage, null);
    }
}
