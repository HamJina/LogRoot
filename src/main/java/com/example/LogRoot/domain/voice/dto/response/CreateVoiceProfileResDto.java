package com.example.LogRoot.domain.voice.dto.response;

import com.example.LogRoot.domain.voice.entity.VoiceProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVoiceProfileResDto {

    @JsonProperty("voice_profile_id")
    private String voiceProfileId;

    private String name;

    @JsonProperty("sample_audio_url")
    private String sampleAudioUrl;

    @JsonProperty("created_at")
    private String createdAt;

    /**
     * 엔티티를 DTO로 변환하는 정적 메서드
     */
    public static CreateVoiceProfileResDto from(VoiceProfile entity) {
        return CreateVoiceProfileResDto.builder()
                .voiceProfileId(entity.getVoiceProfileId())
                .name(entity.getName())
                .sampleAudioUrl(entity.getSampleAudioUrl())
                .createdAt(entity.getCreatedAt().toString())
                .build();
    }
}