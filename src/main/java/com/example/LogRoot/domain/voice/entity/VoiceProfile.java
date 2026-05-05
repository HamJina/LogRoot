package com.example.LogRoot.domain.voice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "voice_profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class VoiceProfile {

    @Id
    @Column(name = "voice_profile_id", length = 50)
    private String voiceProfileId;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "sample_audio_key", nullable = false, length = 255)
    private String sampleAudioKey;

    @Column(name = "sample_audio_url", nullable = false, length = 500)
    private String sampleAudioUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 새로운 보이스 프로필 생성을 위한 정적 팩토리 메서드
     */
    public static VoiceProfile createProfile(String profileId, String userId, String name, String objectKey, String audioUrl) {
        return VoiceProfile.builder()
                .voiceProfileId(profileId)
                .userId(userId)
                .name(name)
                .sampleAudioKey(objectKey)
                .sampleAudioUrl(audioUrl)
                .createdAt(LocalDateTime.now())
                .build();
    }
}