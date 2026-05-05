package com.example.LogRoot.domain.voice.controller;

import com.example.LogRoot.domain.voice.dto.response.CreateVoiceProfileResDto;
import com.example.LogRoot.domain.voice.dto.response.VoiceProfilesListResDto;
import com.example.LogRoot.domain.voice.service.VoiceProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/voice-profiles")
@RequiredArgsConstructor
public class VoiceProfileController {
    private final VoiceProfileService voiceProfileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CreateVoiceProfileResDto createVoiceProfile(
            @RequestPart("file") MultipartFile file, // 음성 파일 (.wav, .mp3)
            @RequestPart("name") String name) { // 음성 파일 이름

        return voiceProfileService.createVoiceProfile(file, name);
    }

    @GetMapping
    public VoiceProfilesListResDto getVoiceProfiles() {
        String userId = "user_001";
        return voiceProfileService.getVoiceProfiles(userId);
    }
}
