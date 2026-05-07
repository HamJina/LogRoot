package com.example.LogRoot.domain.job.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import java.util.List;

@Builder
public record GetJobStatusResDto(
        String job_id,
        String status,
        ProgressInfo progress,
        ErrorInfo error
) {
    public record ProgressInfo(
            String current_step,
            List<StepInfo> steps,
            int overall_pct
    ) {}

    public record StepInfo(
            String name,
            String status,
            int pct
    ) {}

    @JsonInclude(JsonInclude.Include.NON_NULL) // 에러가 없으면 null 제외
    public record ErrorInfo(
            String failed_step,
            String error_code,
            String message
    ) {}
}