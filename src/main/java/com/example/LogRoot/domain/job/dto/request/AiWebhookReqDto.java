package com.example.LogRoot.domain.job.dto.request;

import java.util.List;
import java.util.Map;

public record AiWebhookReqDto() {
    public record Progress(
            String job_id,
            String status,
            Map<String, Object> progress, // 복잡한 구조이므로 Map으로 받아 JSON 문자열로 변환
            String updated_at
    ) {}

    public record Completion(
            String job_id,
            String status,
            List<Object> clips, // 클립 목록
            String completed_at
    ) {}

    public record Failure(
            String job_id,
            String status,
            String failed_step,
            String error_code,
            String error_message,
            String failed_at
    ) {}
}