package com.example.LogRoot.domain.job.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CreateJobReqDto(
        @Min(1) @Max(10) Integer clip_count,
        ClipDurationRange clip_duration_range,
        String content_type, // lecture, entertainment, vlog
        String language,
        Boolean tts_enabled,
        String tts_mode,
        String voice_profile_id,
        String preset_voice_id,
        @DecimalMin("0.8") @DecimalMax("1.5") Float tts_speed,
        String subtitle_style,
        Boolean music_enabled,
        String music_mood,
        String webhook_url
) {
    public record ClipDurationRange(Integer min_sec, Integer max_sec) {}
}