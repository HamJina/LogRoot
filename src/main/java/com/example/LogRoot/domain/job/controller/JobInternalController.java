package com.example.LogRoot.domain.job.controller;

import com.example.LogRoot.domain.job.dto.request.AiWebhookReqDto;
import com.example.LogRoot.domain.job.exception.JobErrorCode;
import com.example.LogRoot.domain.job.exception.JobException;
import com.example.LogRoot.domain.job.service.JobWebhookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/internal/ai/jobs")
@RequiredArgsConstructor
public class JobInternalController {

    private final JobWebhookService webhookService;

    @Value("${ai-server.webhook-token}")
    private String expectedToken;

    private void validateToken(String authHeader) {
        if (authHeader == null || !authHeader.equals("Bearer " + expectedToken)) {
            throw new JobException(JobErrorCode.UNAUTHORIZED_WEBHOOK);
        }
    }

    @PostMapping("/{job_id}/progress")
    public ResponseEntity<Map<String, Boolean>> onProgress(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("job_id") String jobId,
            @RequestBody AiWebhookReqDto.Progress req) throws JsonProcessingException {

        validateToken(token);
        webhookService.handleProgress(jobId, req);
        return ResponseEntity.ok(Map.of("received", true));
    }

    @PostMapping("/{job_id}/completion")
    public ResponseEntity<Map<String, Boolean>> onCompletion(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("job_id") String jobId,
            @RequestBody AiWebhookReqDto.Completion req) throws JsonProcessingException {

        validateToken(token);
        webhookService.handleCompletion(jobId, req);
        return ResponseEntity.ok(Map.of("received", true));
    }

    @PostMapping("/{job_id}/failure")
    public ResponseEntity<Map<String, Boolean>> onFailure(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("job_id") String jobId,
            @RequestBody AiWebhookReqDto.Failure req) {

        validateToken(token);
        webhookService.handleFailure(jobId, req);
        return ResponseEntity.ok(Map.of("received", true));
    }
}