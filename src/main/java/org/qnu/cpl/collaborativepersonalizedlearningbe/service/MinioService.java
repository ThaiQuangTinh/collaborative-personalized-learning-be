package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {


    void uploadFile(MultipartFile file, String bucketName, String objectName);

    InputStreamResource getFileStream(String bucketName, String objectName);

    String getFileUrl(String bucketName, String objectName);

    void deleteFile(String bucketName, String objectName);

}
