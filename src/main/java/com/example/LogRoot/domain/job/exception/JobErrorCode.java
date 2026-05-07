package com.example.LogRoot.domain.job.exception;

import com.example.LogRoot.global.common.error.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JobErrorCode implements ErrorCode {

    // --- 400 BAD REQUEST ---
    INVALID_JOB_OPTIONS(HttpStatus.BAD_REQUEST, "작업 요청 옵션이 올바르지 않습니다."),
    INVALID_JOB_STATUS(HttpStatus.BAD_REQUEST, "현재 작업 상태에서는 해당 요청을 수행할 수 없습니다."),

    // --- 401 UNAUTHORIZED ---
    UNAUTHORIZED_WEBHOOK(HttpStatus.UNAUTHORIZED, "웹훅 인증 토큰이 유효하지 않습니다."),

    // --- 404 NOT FOUND ---
    JOB_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 작업(job_id)을 찾을 수 없습니다."),

    // --- 409 CONFLICT ---
    JOB_NOT_DONE(HttpStatus.CONFLICT, "작업이 아직 완료되지 않아 결과를 조회할 수 없습니다."),

    // --- 500 INTERNAL SERVER ERROR ---
    JSON_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JSON 데이터 처리 중 오류가 발생했습니다."),

    // --- 502 BAD GATEWAY (AI 서버 관련) ---
    AI_SERVER_REQUEST_FAILED(HttpStatus.BAD_GATEWAY, "AI 서버로 작업을 요청하는 데 실패했습니다."),
    AI_SERVER_CONNECTION_ERROR(HttpStatus.BAD_GATEWAY, "AI 서버와 통신이 원활하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}