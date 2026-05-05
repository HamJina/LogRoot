package com.example.LogRoot.domain.voice.dto.response;

import com.example.LogRoot.domain.voice.entity.VoiceProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class VoiceProfilesListResDto {
    @JsonProperty("voice_profiles")
    private List<VoiceProfileDto> voiceProfiles;

    public static VoiceProfilesListResDto from(List<VoiceProfile> entities) {
        List<VoiceProfileDto> dtoList = entities.stream()
                .map(VoiceProfileDto::from)
                .collect(Collectors.toList());

        return new VoiceProfilesListResDto(dtoList);
    }
}