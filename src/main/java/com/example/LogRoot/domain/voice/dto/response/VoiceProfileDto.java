package com.example.LogRoot.domain.voice.dto.response;

import com.example.LogRoot.domain.voice.entity.VoiceProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class VoiceProfileDto {

    @JsonProperty("voice_profile_id")
    private String voiceProfileId;

    private String name;

    @JsonProperty("sample_audio_url")
    private String sampleAudioUrl;

    @JsonProperty("created_at")
    private String createdAt;

    /**
     * Entity -> DTO 변환 메서드
     */
    public static VoiceProfileDto from(VoiceProfile entity) {
        return VoiceProfileDto.builder()
                .voiceProfileId(entity.getVoiceProfileId())
                .name(entity.getName())
                .sampleAudioUrl(entity.getSampleAudioUrl())
                .createdAt(entity.getCreatedAt().toString())
                .build();
    }
}