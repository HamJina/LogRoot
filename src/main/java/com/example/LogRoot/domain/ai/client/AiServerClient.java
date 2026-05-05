package com.example.LogRoot.domain.ai.client;

import com.example.LogRoot.domain.job.dto.request.AiServerJobReqDto;
import com.example.LogRoot.domain.job.dto.request.CreateJobReqDto;
import com.example.LogRoot.domain.job.entity.Job;
import com.example.LogRoot.domain.job.repository.JobRepository;
import com.example.LogRoot.domain.job.type.JobStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiServerClient {

    public static final String AI_SERVER_INTERNAL_JOBS_URL = "/internal/jobs";
    private final RestTemplate restTemplate;
    private final JobRepository jobRepository;

    @Value("${ai-server.url}")
    private String aiServerUrl;

    @Value("${ai-server.webhook-token}")
    private String webhookToken;

    @Async
    public void sendJobRequest(Job job, CreateJobReqDto options, String refAudioUrl) {
        log.info("AI 서버로 작업 요청 전송 시작 - JobID: {}", job.getJobId());

        try {
            AiServerJobReqDto aiReq = AiServerJobReqDto.of(job, options, refAudioUrl, webhookToken);

            restTemplate.postForEntity(aiServerUrl + AI_SERVER_INTERNAL_JOBS_URL, aiReq, Void.class);
            updateJobStatus(job.getJobId(), JobStatus.PROCESSING, null, null);

            log.info("AI 서버 요청 성공 - JobID: {}", job.getJobId());

        } catch (Exception e) {
            log.error("AI 서버 요청 실패 - JobID: {}, Error: {}", job.getJobId(), e.getMessage());

            String errorCode = "AI_SERVER_ERROR";
            String errorMessage = e.getMessage();

            if (e instanceof org.springframework.web.client.ResourceAccessException) {
                errorCode = "AI_SERVER_CONNECTION_TIMEOUT";
            }

            updateJobStatus(job.getJobId(), JobStatus.FAILED, errorCode, errorMessage);
        }
    }

    private void updateJobStatus(String jobId, JobStatus status, String errorCode, String errorMessage) {
        jobRepository.findById(jobId).ifPresent(targetJob -> {
            if (status == JobStatus.FAILED) {
                targetJob.fail(errorCode, errorMessage); // 엔티티에 만든 fail 메서드 활용
            } else {
                targetJob.updateStatus(status);
            }
            jobRepository.save(targetJob);
        });
    }
}
