package com.example.LogRoot.domain.voice.controller;

import com.example.LogRoot.domain.voice.dto.response.PresetVoiceListResDto;
import com.example.LogRoot.domain.voice.service.PresetVoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TTS", description = "TTS 프리셋 및 음성 관련 API")
@RestController
@RequestMapping("/api/v1/tts")
@RequiredArgsConstructor
public class PresetVoiceController {
    private final PresetVoiceService presetVoiceService;

    @Operation(summary = "프리셋 음성 목록 조회", description = "AI 서버로부터 사용 가능한 시스템 기본 음성(프리셋) 목록을 가져옵니다.")
    @GetMapping("/preset-voices")
    public PresetVoiceListResDto getPresetVoices() {
        return presetVoiceService.getPresetVoices();
    }
}
