package com.example.LogRoot.global.minio.service;

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

    @Value("${minio.bucket}")
    private String bucketName;

    /**
     * 파일을 MinIO에 업로드하고 접근 가능한 URL을 반환합니다.
     */
    public String uploadFile(MultipartFile file, String objectKey) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return String.format("%s/%s/%s", endpoint, bucketName, objectKey);

        } catch (Exception e) {
            log.error("MinIO upload failed: {}", e.getMessage());
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }
}