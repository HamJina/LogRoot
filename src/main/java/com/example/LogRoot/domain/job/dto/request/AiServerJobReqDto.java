package com.example.LogRoot.domain.job.dto.request;

import com.example.LogRoot.domain.job.entity.Job;

public record AiServerJobReqDto(
        String job_id,
        String source_video_url,
        String source_video_key,
        String content_type,
        Integer clip_count,
        CreateJobReqDto.ClipDurationRange clip_duration_range,
        String language,
        AiTtsSettings tts,
        AiSubtitleSettings subtitle,
        AiMusicSettings music,
        AiCallbackSettings callback
) {

    public static final String INTERNAL_AI_JOBS = "http://localhost:8080/api/v1/internal/ai/jobs/";
    public static final String PROGRESS = "/progress";
    public static final String COMPLETION = "/completion";
    public static final String FAILURE = "/failure";

    /**
     * Job 엔티티와 요청 옵션, 추가 데이터를 결합하여 AI 서버용 DTO를 생성합니다.
     */
    public static AiServerJobReqDto of(Job job, CreateJobReqDto options, String refAudioUrl, String webhookToken) {
        String baseUrl = INTERNAL_AI_JOBS + job.getJobId();

        return new AiServerJobReqDto(
                job.getJobId(),
                job.getSourceVideoUrl(),
                job.getSourceVideoKey(),
                options.content_type(),
                options.clip_count(),
                options.clip_duration_range(),
                options.language(),
                new AiTtsSettings(
                        options.tts_enabled(),
                        options.tts_mode(),
                        options.voice_profile_id(),
                        options.preset_voice_id(),
                        refAudioUrl,
                        options.tts_speed()
                ),
                new AiSubtitleSettings(options.subtitle_style()),
                new AiMusicSettings(options.music_enabled(), options.music_mood()),
                new AiCallbackSettings(
                        baseUrl + PROGRESS,
                        baseUrl + COMPLETION,
                        baseUrl + FAILURE,
                        webhookToken
                )
        );
    }

    public record AiTtsSettings(boolean enabled, String mode, String voice_profile_id, String preset_voice_id, String reference_audio_url, float speed) {}
    public record AiSubtitleSettings(String style) {}
    public record AiMusicSettings(boolean enabled, String mood) {}
    public record AiCallbackSettings(String progress_url, String completion_url, String failure_url, String token) {}
}