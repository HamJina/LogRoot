package com.example.LogRoot.global.common.response;

import com.example.LogRoot.global.common.error.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GlobalResponse<T> {
    private final int status;
    private final boolean success;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL) // 데이터가 null일 경우 JSON에서 제외 (선택 사항)
    private final T data;

    public static <T> GlobalResponse<T> success(SuccessCode successCode, T data) {
        return GlobalResponse.<T>builder()
                .success(true)
                .status(successCode.getStatus())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    public static <T> GlobalResponse<T> success(SuccessCode successCode) {
        return success(successCode, null);
    }

    public static <T> GlobalResponse<T> success(int status, T data) {
        return GlobalResponse.<T>builder()
                .success(true)
                .status(status)
                .message("요청이 성공적으로 처리되었습니다.")
                .data(data)
                .build();
    }

    public static <T> GlobalResponse<T> failure(int status, T errorResponse) {
        return GlobalResponse.<T>builder()
                .success(false)
                .status(status)
                .message("요청 처리에 실패하였습니다.")
                .data(errorResponse)
                .build();
    }

    public static <T> GlobalResponse<T> failure(int status, String message, T errorResponse) {
        return GlobalResponse.<T>builder()
                .success(false)
                .status(status)
                .message(message)
                .data(errorResponse)
                .build();
    }
}