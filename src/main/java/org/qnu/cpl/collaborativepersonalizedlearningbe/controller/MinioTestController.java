package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import io.minio.MinioClient;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MinioTestController {

    private final MinioClient minioClient;


    @GetMapping("/test-minio")
    public Object testMinio() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();

        for (Bucket bucket : buckets) {
            System.out.println("🪣 Bucket: " + bucket.name());
        }

        return buckets.stream()
                .map(b -> Map.of(
                        "name", b.name(),
                        "creationDate", b.creationDate().toString()
                ))
                .toList();
    }


}
