package com.example.LogRoot.domain.voice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PresetVoiceListResDto {

    @JsonProperty("preset_voices")
    private List<PresetVoiceDto> presetVoices;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PresetVoiceDto {
        @JsonProperty("preset_voice_id")
        private String presetVoiceId;

        private String name;
        private String language;
        private String description;
    }
}