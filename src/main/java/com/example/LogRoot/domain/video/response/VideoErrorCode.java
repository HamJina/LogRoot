package com.example.LogRoot.domain.video.response;

import com.example.LogRoot.global.common.error.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VideoErrorCode implements ErrorCode {
    VIDEO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 비디오를 찾을 수 없습니다.");
    private final HttpStatus status;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
