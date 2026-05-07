package com.example.LogRoot.domain.job.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GetClipsResDto(
        String job_id,
        List<ClipInfo> clips
) {
    public record ClipInfo(
            String clip_id,
            int rank,
            String title,
            double score,
            Segment segment,
            String highlight_reason,
            List<String> emotion_tags,
            Assets assets,
            TtsInfo tts,
            SubtitleInfo subtitle,
            MusicInfo music,
            LocalDateTime created_at
    ) {}

    public record Segment(double start_sec, double end_sec, double duration_sec) {}

    public record Assets(
            String video_url,
            String thumbnail_url,
            String subtitle_url,
            String tts_audio_url,
            String raw_video_url
    ) {}

    public record TtsInfo(boolean enabled, String mode, double speed, String voice_profile_id) {}

    public record SubtitleInfo(String style, boolean burned_in) {}

    public record MusicInfo(
            boolean enabled,
            String track_id,
            String title,
            String mood,
            Integer bpm,
            Integer volume_db
    ) {}
}