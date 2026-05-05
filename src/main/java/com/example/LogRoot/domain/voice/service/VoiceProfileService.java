package com.example.LogRoot.domain.voice.service;

import com.example.LogRoot.domain.voice.dto.response.CreateVoiceProfileResDto;
import com.example.LogRoot.domain.voice.dto.response.VoiceProfileDto;
import com.example.LogRoot.domain.voice.dto.response.VoiceProfilesListResDto;
import com.example.LogRoot.domain.voice.entity.VoiceProfile;
import com.example.LogRoot.domain.voice.exception.VoiceErrorCode;
import com.example.LogRoot.domain.voice.exception.VoiceException;
import com.example.LogRoot.domain.voice.repository.VoiceProfileRepository;
import com.example.LogRoot.global.common.path.StoragePath;
import com.example.LogRoot.global.minio.service.MinioService;
import com.sun.jdi.VoidValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoiceProfileService {
    private final VoiceProfileRepository voiceProfileRepository;
    private final MinioService minioService;

    @Transactional
    public CreateVoiceProfileResDto createVoiceProfile(MultipartFile file, String name) {
        String profileId = "vp_" + UUID.randomUUID().toString().substring(0, 8);
        String userId = "user_001"; // 수정 예정

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        validateAudioExtension(extension);

        String objectKey = StoragePath.getVoiceProfilePath(userId, profileId, extension);
        String audioUrl = minioService.uploadFile(file, objectKey);

        VoiceProfile profile = VoiceProfile.createProfile(profileId, userId, name, objectKey, audioUrl);
        voiceProfileRepository.save(VoiceProfile.createProfile(profileId, userId, name, objectKey, audioUrl));

        return CreateVoiceProfileResDto.from(profile);
    }

    @Transactional(readOnly = true)
    public VoiceProfilesListResDto getVoiceProfiles(String userId) {
        List<VoiceProfile> profiles = voiceProfileRepository.findAllByUserId(userId);

        return VoiceProfilesListResDto.from(profiles);
    }

    private void validateAudioExtension(String extension) {
        if (extension == null ||
                !(extension.equalsIgnoreCase("wav") || extension.equalsIgnoreCase("mp3"))) {
            throw new VoiceException(VoiceErrorCode.INVALID_AUDIO_FORMAT);
        }
    }
}
