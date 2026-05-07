package com.example.LogRoot.domain.job.exception;

import com.example.LogRoot.global.common.error.exception.CustomException;

public class JobException extends CustomException {
    public JobException(JobErrorCode errorCode) {
        super(errorCode);
    }
}