package com.example.LogRoot.domain.job.controller;

import com.example.LogRoot.domain.job.dto.response.CreateJobResDto;
import com.example.LogRoot.domain.job.dto.response.GetJobStatusResDto;
import com.example.LogRoot.domain.job.service.JobService;
import com.example.LogRoot.global.common.response.GlobalResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Job", description = "숏폼 생성 작업 API")
@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @Operation(summary = "Job 생성", description = "원본 영상을 업로드하고 AI 파이프라인을 실행합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GlobalResponse<CreateJobResDto>> createJob(
            @RequestPart("file") MultipartFile file,
            @RequestPart("options") String options) throws JsonProcessingException {

        CreateJobResDto response = jobService.createJob(file, options);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(GlobalResponse.success(HttpStatus.ACCEPTED.value(), response));
    }

    @Operation(summary = "Job 상태 조회", description = "작업의 진행 단계와 퍼센트를 조회합니다.")
    @GetMapping("/{job_id}/status")
    public ResponseEntity<GetJobStatusResDto> getJobStatus(@PathVariable("job_id") String jobId) {
        return ResponseEntity.ok(jobService.getJobStatus(jobId));
    }
}
