package com.example.LogRoot.global.common.error.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();
    String getMessage();
    String getName(); // Enum의 name()을 받기 위함
}