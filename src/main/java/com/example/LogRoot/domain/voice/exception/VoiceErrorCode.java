package com.example.LogRoot.domain.voice.exception;
import com.example.LogRoot.global.common.error.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VoiceErrorCode implements ErrorCode {

    // 파일 관련
    INVALID_AUDIO_FORMAT(HttpStatus.BAD_REQUEST, "허용되지 않는 파일 형식입니다. (.wav, .mp3만 가능)"),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다."),

    // 조회 관련
    VOICE_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 음성 프로필을 찾을 수 없습니다."),

    // AI 서버 통신 관련
    AI_SERVER_CONNECTION_ERROR(HttpStatus.BAD_GATEWAY, "AI 서버와 통신 중 오류가 발생했습니다."),
    PRESET_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 프리셋 보이스가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
