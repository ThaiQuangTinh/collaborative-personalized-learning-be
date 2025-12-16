package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateFileResourceRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLinkResourceRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ResourceResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ResourceUrlResponse;
import org.springframework.core.io.InputStreamResource;

import java.util.List;

public interface ResourceService {

    ResourceResponse createFileResource(String userId, CreateFileResourceRequest request);

    ResourceResponse createLinkResource(String userId, CreateLinkResourceRequest request);

    void deleteResource(String resourceId);

    InputStreamResource getResourceFile(String resourceId, String[] outFileName, String[] outMimeType);

    ResourceUrlResponse getResourceUrl(String resourceId);

    List<ResourceResponse> getAllResourcesByLessonId(String lessonId);

}
