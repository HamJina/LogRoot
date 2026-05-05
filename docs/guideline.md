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

### 주요 처리 흐름

**Job 생성 시**

```
1. options JSON 파싱 및 검증
2. 파일을 MinIO에 저장
   → key: input/jobs/{job_id}/source.{ext}
3. jobs 테이블에 status=queued로 INSERT
4. AI 서버에 POST /internal/jobs 요청
5. 요청 성공 시 status=processing 업데이트
6. 202 응답 반환
```

**진행 웹훅 수신 시**

```
1. Authorization 헤더 토큰 검증
2. jobs 테이블 progress_json, status, updated_at 업데이트
3. 200 응답 반환
```

**완료 웹훅 수신 시**

```
1. Authorization 헤더 토큰 검증
2. jobs 테이블 status=done, completed_at 업데이트
3. job_clips 테이블에 clips 전체 INSERT
4. 200 응답 반환
```

**실패 웹훅 수신 시**

```
1. Authorization 헤더 토큰 검증
2. jobs 테이블 status=failed, error_code, error_message 업데이트
3. 200 응답 반환
```

### 반드시 구현해야 하는 것

- 웹훅 토큰 검증 (Authorization: Bearer)
- 완료 웹훅 중복 수신 무해 처리 (idempotent)
- `GET /api/v1/jobs/{job_id}/clips` — done 이전이면 409 반환
- preset voice 목록을 AI 서버에서 가져와 캐시 또는 프록시

### 하지 않아야 하는 것

- TTS 생성 직접 수행
- 자막 burn-in 직접 수행
- 음악 선택 또는 믹싱 직접 수행