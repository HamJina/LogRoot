package com.example.LogRoot.global.common.path;

public class StoragePath {

    // 버킷 이름
    public static final String BUCKET_NAME = "shortform-bucket";

    // 공통 루트 경로
    private static final String INPUT_ROOT = "input";
    private static final String OUTPUT_ROOT = "output";

    /**
     * Voice Profile 저장 경로 생성
     * 규칙: input/voice-profiles/{userId}/{profileId}/sample.{ext}
     */
    public static String getVoiceProfilePath(String userId, String profileId, String extension) {
        return String.format("%s/voice-profiles/%s/%s/sample.%s",
                INPUT_ROOT, userId, profileId, extension);
    }

    /**
     * 원본 비디오 업로드 경로 생성
     * 규칙: input/jobs/{jobId}/source.{ext}
     */
    public static String RawVideoPath(String jobId, String extension) {
        return String.format("%s/jobs/%s/source.%s",
                INPUT_ROOT, jobId, extension);
    }

    /**
     * 최종 클립 결과물 저장 경로
     * 규칙: output/jobs/{jobId}/clips/{clipId}/final.mp4
     */
    public static String getClipOutputPath(String jobId, String clipId) {
        return String.format("%s/jobs/%s/clips/%s/final.mp4",
                OUTPUT_ROOT, jobId, clipId);
    }

    /**
     * 썸네일 저장 경로
     */
    public static String getThumbnailPath(String jobId, String clipId) {
        return String.format("%s/jobs/%s/clips/%s/thumb.jpg",
                OUTPUT_ROOT, jobId, clipId);
    }
}