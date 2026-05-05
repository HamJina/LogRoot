package com.example.LogRoot.global.minio.service;

import com.example.LogRoot.global.common.path.StoragePath;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * 파일을 MinIO에 업로드하고 접근 가능한 URL을 반환합니다.
     */
    public String uploadFile(MultipartFile file, String objectKey) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(StoragePath.BUCKET_NAME)
                            .object(objectKey)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // 업로드된 파일의 전체 URL 반환 (직접 접근 주소 생성)
            // 도커 환경이나 로컬 환경에 따라 형식이 다를 수 있으니 endpoint를 기반으로 생성합니다.
            return String.format("%s/%s/%s", endpoint, StoragePath.BUCKET_NAME, objectKey);

        } catch (Exception e) {
            log.error("MinIO upload failed: {}", e.getMessage());
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }
}