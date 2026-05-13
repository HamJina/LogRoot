package com.example.LogRoot.domain.job.entity;

import com.example.LogRoot.domain.job.type.JobStatus;
import com.example.LogRoot.global.common.mapped.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Job extends BaseTimeEntity {

    @Id
    @Column(name = "job_id", length = 50)
    private String jobId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status;

    @Column(name = "source_video_key", nullable = false)
    private String sourceVideoKey;

    @Column(name = "source_video_url", nullable = false)
    private String sourceVideoUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "options_json", columnDefinition = "jsonb")
    private String optionsJson;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "progress_json", columnDefinition = "jsonb")
    private String progressJson;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "result_json", columnDefinition = "jsonb") // 💡 name 속성 명시적으로 추가
    private String resultJson;

    // --- 에러 및 완료 관련 필드 추가 ---

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public void updateStatus(JobStatus status) {
        this.status = status;
        if (status == JobStatus.DONE) {
            this.completedAt = LocalDateTime.now();
        }
    }

    public void fail(String errorCode, String errorMessage) {
        this.status = JobStatus.FAILED;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static Job createJob(String jobId, String userId, String key, String url, String optionsJson) {
        return Job.builder()
                .jobId(jobId)
                .userId(userId)
                .sourceVideoKey(key)
                .sourceVideoUrl(url)
                .optionsJson(optionsJson)
                .status(JobStatus.QUEUED)
                .build();
    }

    public void complete(String resultJson) {
        this.status = JobStatus.DONE;
        this.resultJson = resultJson;
        this.completedAt = LocalDateTime.now();
    }

    public void updateProgress(String progressJson) {
        this.status = JobStatus.PROCESSING;
        this.progressJson = progressJson;
    }
}