package com.example.LogRoot.domain.job.exception;

import com.example.LogRoot.global.common.error.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JobErrorCode implements ErrorCode {

    // 조회 관련
    JOB_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 작업(Job)을 찾을 수 없습니다."),

    // 파일 및 데이터 관련
    INVALID_JOB_OPTIONS(HttpStatus.BAD_REQUEST, "작업 요청 옵션이 올바르지 않습니다."),
    JSON_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 파싱 중 오류가 발생했습니다."),

    // 상태 관련
    INVALID_JOB_STATUS(HttpStatus.BAD_REQUEST, "현재 작업 상태에서는 해당 요청을 수행할 수 없습니다."),

    // AI 서버 파이프라인 및 통신 관련
    AI_SERVER_REQUEST_FAILED(HttpStatus.BAD_GATEWAY, "AI 서버로 작업을 전달하는 데 실패했습니다."),
    AI_PIPELINE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AI 처리 파이프라인 진행 중 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}