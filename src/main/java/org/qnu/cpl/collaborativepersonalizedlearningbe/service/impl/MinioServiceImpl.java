package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.config.UploadProperties;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.MinioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    private final UploadProperties uploadProperties;

    @Value("${minio.url}")
    private String minioUrl;

    @Override
    public void uploadFile(MultipartFile file, String bucketName, String objectName) {
        System.out.println("Go here!");

        try {
            long maxSizeBytes = uploadProperties.getMaxSizeMb() * 1024L * 1024L;
            if (file.getSize() > maxSizeBytes) {
                throw new AppException(ErrorCode.FILE_SIZE_TOO_LARGE);
            }

            String contentType = file.getContentType();
            if (!uploadProperties.getAllowedTypes().contains(contentType)) {
                throw new AppException(ErrorCode.FILE_TYPE_NOT_ALLOWED);
            }

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(contentType)
                                .build()
                );
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.MINIO_UPLOAD_FAILED);
        }
    }

    @Override
    public InputStreamResource getFileStream(String bucketName, String objectName) {
        try {
            InputStream is = minioClient.getObject(
                    io.minio.GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return new InputStreamResource(is);
        } catch (Exception e) {
            throw new AppException(ErrorCode.MINIO_GET_FAILED);
        }
    }

    @Override
    public String getFileUrl(String bucketName, String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(24, TimeUnit.HOURS)
                            .build()
            );
        } catch (Exception e) {
//            throw new AppException(ErrorCode.MINIO_URL_GENERATE_FAILED);
            return null;
        }
    }

    @Override
    public void deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new AppException(ErrorCode.MINIO_DELETE_FAILED);
        }
    }
}

