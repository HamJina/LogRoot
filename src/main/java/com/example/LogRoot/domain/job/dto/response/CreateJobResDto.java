package com.example.LogRoot.domain.job.dto.response;

import com.example.LogRoot.domain.job.entity.Job;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record CreateJobResDto(
        @Schema(description = "생성된 작업 ID", example = "job_e65b744b")
        String job_id,

        @Schema(description = "현재 작업 상태", example = "QUEUED")
        String status,

        @Schema(description = "작업 생성 시각")
        LocalDateTime created_at,

        @Schema(description = "예상 소요 시간 (초)", example = "180")
        int estimated_duration_sec,

        @Schema(description = "관련 리소스 링크 정보")
        JobLinks _links
) {
    public static CreateJobResDto from(Job job) {
        String baseUrl = "/api/v1/jobs/" + job.getJobId();

        return CreateJobResDto.builder()
                .job_id(job.getJobId())
                .status(job.getStatus().name())
                .created_at(job.getCreatedAt())
                .estimated_duration_sec(180) // 비즈니스 로직에 따라 산출
                ._links(new JobLinks(
                        baseUrl,
                        baseUrl + "/status",
                        baseUrl + "/clips"
                ))
                .build();
    }

    public record JobLinks(
            String self,
            String status,
            String clips
    ) {}
}