package org.qnu.cpl.collaborativepersonalizedlearningbe.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MinioInitializer {

    private final MinioClient minioClient;

    @Value("${minio.buckets.resources}")
    private String resourcesBucket;

    @Value("${minio.buckets.avatars}")
    private String avatarsBucket;

    @PostConstruct
    public void init() {
        createBucketIfNotExist(resourcesBucket);
        createBucketIfNotExist(avatarsBucket);
    }

    private void createBucketIfNotExist(String bucketName) {
        try {
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!found) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucketName).build()
                );
                System.out.println("Created bucket: " + bucketName);
            } else {
                System.out.println("Bucket already exists: " + bucketName);
            }
        } catch (Exception e) {
            System.err.println("Error creating bucket " + bucketName + ": " + e.getMessage());
        }
    }
}

