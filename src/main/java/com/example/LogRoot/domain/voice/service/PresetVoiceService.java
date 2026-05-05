package com.example.LogRoot.domain.voice.service;

import com.example.LogRoot.domain.voice.dto.response.PresetVoiceListResDto;
import com.example.LogRoot.domain.voice.exception.VoiceErrorCode;
import com.example.LogRoot.domain.voice.exception.VoiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PresetVoiceService {

    public static final String INTERNAL_TTS_PRESET_VOICES = "/internal/tts/preset-voices";
    private final RestTemplate restTemplate;

    @Value("${ai-server.url}")
    private String aiServerUrl;

    public PresetVoiceListResDto getPresetVoices() {
        try {
            String targetUrl = aiServerUrl + INTERNAL_TTS_PRESET_VOICES;

            return restTemplate.getForObject(targetUrl, PresetVoiceListResDto.class);

        } catch (Exception e) {
            throw new VoiceException(VoiceErrorCode.PRESET_NOT_FOUND);
        }
    }
}
