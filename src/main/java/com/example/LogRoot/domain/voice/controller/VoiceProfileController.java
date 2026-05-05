package com.example.LogRoot.domain.voice.controller;

import com.example.LogRoot.domain.voice.dto.response.CreateVoiceProfileResDto;
import com.example.LogRoot.domain.voice.dto.response.VoiceProfilesListResDto;
import com.example.LogRoot.domain.voice.service.VoiceProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Voice Profile", description = "음성 프로필 관리 API")
@RestController
@RequestMapping("/api/v1/voice-profiles")
@RequiredArgsConstructor
public class VoiceProfileController {
    private final VoiceProfileService voiceProfileService;

    @Operation(summary = "음성 프로필 생성", description = ".wav, .mp3 파일을 업로드하여 나만의 음성 모델을 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CreateVoiceProfileResDto createVoiceProfile(
            @RequestPart("file") MultipartFile file, // 음성 파일 (.wav, .mp3)
            @RequestPart("name") String name) { // 음성 파일 이름

        return voiceProfileService.createVoiceProfile(file, name);
    }

    @Operation(summary = "내 음성 프로필 목록 조회")
    @GetMapping
    public VoiceProfilesListResDto getVoiceProfiles() {
        String userId = "user_001";
        return voiceProfileService.getVoiceProfiles(userId);
    }
}
