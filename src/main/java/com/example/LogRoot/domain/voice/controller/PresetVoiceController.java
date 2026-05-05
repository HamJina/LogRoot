package com.example.LogRoot.domain.voice.controller;

import com.example.LogRoot.domain.voice.dto.response.CreateVoiceProfileResDto;
import com.example.LogRoot.domain.voice.dto.response.PresetVoiceListResDto;
import com.example.LogRoot.domain.voice.dto.response.VoiceProfilesListResDto;
import com.example.LogRoot.domain.voice.service.PresetVoiceService;
import com.example.LogRoot.domain.voice.service.VoiceProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/tts")
@RequiredArgsConstructor
public class PresetVoiceController {
    private final PresetVoiceService presetVoiceService;

    @GetMapping("/preset-voices")
    public PresetVoiceListResDto getPresetVoices() {
        return presetVoiceService.getPresetVoices();
    }
}
