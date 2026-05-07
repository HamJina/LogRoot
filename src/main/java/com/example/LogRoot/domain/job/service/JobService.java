package com.example.LogRoot.domain.job.service;

import com.example.LogRoot.domain.ai.client.AiServerClient;
import com.example.LogRoot.domain.job.dto.request.CreateJobReqDto;
import com.example.LogRoot.domain.job.dto.response.CreateJobResDto;
import com.example.LogRoot.domain.job.dto.response.GetClipsResDto;
import com.example.LogRoot.domain.job.dto.response.GetJobStatusResDto;
import com.example.LogRoot.domain.job.entity.Job;
import com.example.LogRoot.domain.job.exception.JobErrorCode;
import com.example.LogRoot.domain.job.exception.JobException;
import com.example.LogRoot.domain.job.repository.JobRepository;
import com.example.LogRoot.domain.job.type.JobStatus;
import com.example.LogRoot.domain.voice.entity.VoiceProfile;
import com.example.LogRoot.domain.voice.repository.VoiceProfileRepository;
import com.example.LogRoot.global.common.error.exception.CustomException;
import com.example.LogRoot.global.common.path.StoragePath;
import com.example.LogRoot.global.minio.service.MinioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobService {
    private final JobRepository jobRepository;
    private final AiServerClient aiServerClient;
    private final VoiceProfileRepository voiceProfileRepository;
    private final MinioService minioService;
    private final ObjectMapper objectMapper;

    public CreateJobResDto createJob(MultipartFile file, String optionsStr) throws JsonProcessingException {
        CreateJobReqDto options = objectMapper.readValue(optionsStr, CreateJobReqDto.class);

        String jobId = "job_" + UUID.randomUUID().toString().substring(0, 8);
        String userId = "user_001";

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String objectKey = StoragePath.getRawVideoPath(jobId, extension);
        String videoUrl = minioService.uploadFile(file, objectKey);

        Job job = saveInitialJob(jobId, userId, objectKey, videoUrl, optionsStr);

        String refAudioUrl = null;
        if (options.tts_enabled() && "voice_clone".equals(options.tts_mode())) {
            refAudioUrl = voiceProfileRepository.findById(options.voice_profile_id())
                    .map(VoiceProfile::getSampleAudioUrl).orElse(null);
        }

        aiServerClient.sendJobRequest(job, options, refAudioUrl);

        return CreateJobResDto.from(job);
    }

    @Transactional
    public Job saveInitialJob(String jobId, String userId, String key, String url, String options) {
        Job job = Job.createJob(jobId, userId, key, url, options);
        return jobRepository.save(job);
    }

    @Transactional(readOnly = true)
    public GetJobStatusResDto getJobStatus(String jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new CustomException(JobErrorCode.JOB_NOT_FOUND));

        GetJobStatusResDto.ProgressInfo progressInfo = null;
        if (job.getProgressJson() != null) {
            try {
                progressInfo = objectMapper.readValue(job.getProgressJson(), GetJobStatusResDto.ProgressInfo.class);
            } catch (JsonProcessingException e) {
                log.error("Failed to parse progress JSON for job: {}", jobId);
            }
        }

        GetJobStatusResDto.ErrorInfo errorInfo = null;
        if (job.getStatus() == JobStatus.FAILED) {
            errorInfo = new GetJobStatusResDto.ErrorInfo(
                     progressInfo != null ? progressInfo.current_step() : "unknown",
                    job.getErrorCode(),
                    job.getErrorMessage()
            );
        }

        return GetJobStatusResDto.builder()
                .job_id(job.getJobId())
                .status(job.getStatus().name().toLowerCase())
                .progress(progressInfo)
                .error(errorInfo)
                .build();
    }

    @Transactional(readOnly = true)
    public GetClipsResDto getJobClips(String jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobException(JobErrorCode.JOB_NOT_FOUND));

        if (job.getStatus() != JobStatus.DONE) {
            throw new JobException(JobErrorCode.JOB_NOT_DONE);
        }

        try {
            String json = job.getResultJson();

            if (json == null) return new GetClipsResDto(jobId, List.of());

            List<GetClipsResDto.ClipInfo> clips = objectMapper.readValue(
                    json,
                    new TypeReference<List<GetClipsResDto.ClipInfo>>() {}
            );

            return GetClipsResDto.builder()
                    .job_id(job.getJobId())
                    .clips(clips)
                    .build();
        } catch (JsonProcessingException e) {
            throw new JobException(JobErrorCode.JSON_PARSING_ERROR);
        }
    }
}