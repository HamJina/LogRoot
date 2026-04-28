package com.example.LogRoot.domain.video.response;

import com.example.LogRoot.global.common.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VideoSuccessCode implements SuccessCode {
    VIDEO_UPLOAD_SUCCESS(201, "비디오 업로드가 완료되었습니다.");
    private final int status;
    private final String message;
}