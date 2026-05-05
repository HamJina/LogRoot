package com.example.LogRoot.domain.voice.service;

import com.example.LogRoot.domain.voice.dto.response.PresetVoiceListResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PresetVoiceService {

    private final RestTemplate restTemplate;

    @Value("${ai-server.url}")
    private String aiServerUrl;

    public PresetVoiceListResDto getPresetVoices() {
        try {
            String targetUrl = aiServerUrl + "/internal/tts/preset-voices";

            return restTemplate.getForObject(targetUrl, PresetVoiceListResDto.class);

        } catch (Exception e) {
            throw new RuntimeException("AI 서버로부터 프리셋 목록을 가져오지 못했습니다.", e);
        }
    }
}
