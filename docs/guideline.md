### 저장소 경로 규칙

```
shortform-bucket/
├── input/
│   ├── jobs/{job_id}/source.{ext}          # 원본 영상
│   └── voice-profiles/{user_id}/{voice_profile_id}/sample.{ext}  # 음성 샘플
└── output/
    └── jobs/{job_id}/clips/{clip_id}/
        ├── final.mp4                        # 최종 영상
        ├── thumb.jpg                        # 썸네일
        ├── subtitle.srt                     # 자막 (disabled면 생성 안 함)
        └── tts.mp3                          # TTS 오디오
```

### 상태 타입 정의
```tsx
type JobOptions = {
  clip_count: number;
  clip_duration_range: { min_sec: number; max_sec: number };
  content_type: "lecture" | "entertainment" | "vlog";
  language: "ko" | "en" | "ja" | "auto";
  tts_enabled: boolean;
  tts_mode: "voice_clone" | "preset" | "disabled";
  voice_profile_id?: string | null;
  preset_voice_id?: string | null;
  tts_speed: number;
  subtitle_style: "dynamic" | "static" | "disabled";
  music_enabled: boolean;
  music_mood: "auto" | "energetic" | "calm" | "funny" | "tense";
};

type JobStatus = "queued" | "processing" | "done" | "failed";

type StepStatus = "pending" | "processing" | "done" | "failed";
```