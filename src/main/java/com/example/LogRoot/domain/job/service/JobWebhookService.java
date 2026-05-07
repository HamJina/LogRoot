package com.example.LogRoot.domain.job.service;

import com.example.LogRoot.domain.job.dto.request.AiWebhookReqDto;
import com.example.LogRoot.domain.job.entity.Job;
import com.example.LogRoot.domain.job.exception.JobErrorCode;
import com.example.LogRoot.domain.job.exception.JobException;
import com.example.LogRoot.domain.job.repository.JobRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobWebhookService {

    private final JobRepository jobRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void handleProgress(String jobId, AiWebhookReqDto.Progress req) throws JsonProcessingException {
        Job job = getJob(jobId);
        String progressJson = objectMapper.writeValueAsString(req.progress());
        job.updateProgress(progressJson); // Job 엔티티에 메서드 추가 필요
    }

    @Transactional
    public void handleCompletion(String jobId, AiWebhookReqDto.Completion req) throws JsonProcessingException {
        Job job = getJob(jobId);
        String resultJson = objectMapper.writeValueAsString(req.clips());
        job.complete(resultJson);
    }

    @Transactional
    public void handleFailure(String jobId, AiWebhookReqDto.Failure req) {
        Job job = getJob(jobId);
        job.fail(req.error_code(), req.error_message());
    }

    private Job getJob(String jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new JobException(JobErrorCode.JOB_NOT_FOUND));
    }
}