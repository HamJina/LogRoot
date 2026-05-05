package com.example.LogRoot.domain.voice.repository;

import com.example.LogRoot.domain.voice.entity.VoiceProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoiceProfileRepository extends JpaRepository<VoiceProfile, String> {
    List<VoiceProfile> findAllByUserId(String userId);
}