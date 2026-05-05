package com.example.LogRoot.domain.job.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobStatus {

    QUEUED("대기 중", "작업이 생성되어 큐에 등록되었습니다."),
    PROCESSING("처리 중", "AI 서버에서 파이프라인이 가동 중입니다."),
    DONE("완료", "모든 클립 생성 및 패키징이 완료되었습니다."),
    FAILED("실패", "처리 도중 오류가 발생했습니다.");

    private final String title;
    private final String description;
}
