package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Lesson;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Resource;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ResourceType;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateFileResourceRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLinkResourceRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateFileResourceResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateLinkResourceResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ResourceResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ResourceUrlResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.LessonRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.ResourceRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.MinioService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.ResourceService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    @Value("${minio.buckets.resources}")
    private String resourceBucket;

    private final ResourceRepository resourceRepository;

    private final UserRepository userRepository;

    private final LessonRepository lessonRepository;

    private final MinioService minioService;

    @Override
    public ResourceResponse createFileResource(String userId, CreateFileResourceRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        String resourceId = UUIDUtil.generate();
        String ext = FilenameUtils.getExtension(request.getFile().getOriginalFilename());
        String storageKey = "lessons/" + lesson.getLessonId() + "/resources/" + resourceId + "." + ext;

        minioService.uploadFile(request.getFile(), resourceBucket, storageKey);

        Resource resource = new Resource();
        resource.setResourceId(resourceId);
        resource.setLesson(lesson);
        resource.setUser(user);
        resource.setName(request.getName());
        resource.setType(ResourceType.FILE);
        resource.setObjectName(storageKey);
        resource.setSizeBytes(request.getFile().getSize());
        resource.setMimeType(request.getFile().getContentType());
        resource.setIsDeleted(false);
        resource.setCreatedAt(LocalDateTime.now());
        resource.setUpdatedAt(LocalDateTime.now());

        resourceRepository.save(resource);

        String resourceUrl = minioService.getFileUrl(resourceBucket, resource.getObjectName());

        return new ResourceResponse(
                resource.getResourceId(),
                resource.getName(),
                resource.getType(),
                resource.getExternalLink(),
                resource.getSizeBytes(),
                resource.getMimeType(),
                resourceUrl
        );
    }

    @Override
    public ResourceResponse createLinkResource(String userId, CreateLinkResourceRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Resource resource = new Resource();
        resource.setResourceId(UUIDUtil.generate());
        resource.setUser(user);
        resource.setLesson(lesson);
        resource.setName(request.getName());
        resource.setExternalLink(request.getExternalLink());
        resource.setType(ResourceType.LINK);
        resource.setIsDeleted(false);
        resource.setCreatedAt(LocalDateTime.now());
        resource.setUpdatedAt(LocalDateTime.now());

        resourceRepository.save(resource);

        return new ResourceResponse(
                resource.getResourceId(),
                resource.getName(),
                resource.getType(),
                resource.getExternalLink(),
                resource.getSizeBytes(),
                resource.getMimeType(),
                ""
        );
    }

    @Override
    public void deleteResource(String resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        if (resource.getType().equals(ResourceType.FILE)) {
            minioService.deleteFile(resourceBucket, resource.getObjectName());
        }

        resource.setIsDeleted(true);
        resource.setUpdatedAt(LocalDateTime.now());

        resourceRepository.save(resource);
    }

    @Override
    public InputStreamResource getResourceFile(String resourceId, String[] outFileName, String[] outMimeType) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        // trả ra tên file và mime type để controller dùng header
        outFileName[0] = resource.getName();
        outMimeType[0] = resource.getMimeType();

        return minioService.getFileStream(resourceBucket, resource.getObjectName());
    }

    public ResourceUrlResponse getResourceUrl(String resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        String objectName = "";

        // If resource have original resource (by export).
        if (resource.getOriginalResourceId() != null) {
            Resource originalResource = resourceRepository.findById(resource.getOriginalResourceId())
                    .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

            objectName = originalResource.getObjectName();
        }

        // If resource is original.
        objectName = resource.getObjectName();

        String resourceUrl = minioService.getFileUrl(resourceBucket, objectName);

        return new ResourceUrlResponse(resourceUrl);
    }

    @Override
    public List<ResourceResponse> getAllResourcesByLessonId(String lessonId) {
        List<Resource> resourceList = resourceRepository.findAllByLesson_LessonId(lessonId);

        List<ResourceResponse> resourceResponses = new ArrayList<>();

        for (Resource resource : resourceList) {
            ResourceResponse res = new ResourceResponse();

            if (resource.getType().equals(ResourceType.FILE)) {
                ResourceUrlResponse urlObject = getResourceUrl(resource.getResourceId());
                res.setResourceUrl(urlObject.getResourceUrl());
            }

            res.setResourceId(resource.getResourceId());
            res.setName(resource.getName());
            res.setType(resource.getType());
            res.setMimeType(resource.getMimeType());
            res.setExternalLink(resource.getExternalLink());
            res.setSizeBytes(resource.getSizeBytes());

            resourceResponses.add(res);
        }

        return resourceResponses;
    }

}
