package com.example.LogRoot.domain.voice.exception;

import com.example.LogRoot.global.common.error.exception.CustomException;

public class VoiceException extends CustomException {
    public VoiceException(VoiceErrorCode errorCode) {
        super(errorCode);
    }
}