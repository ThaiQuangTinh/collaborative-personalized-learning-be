package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Progress;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateProgressRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateProgressRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateProgressResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdateProgressResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProgressService {

    CreateProgressResponse createProgress(String userId, CreateProgressRequest request);

    UpdateProgressResponse updateProgress(String userId, UpdateProgressRequest request);

    void deleteProgress(String lessonId);

    void saveProgress(Progress progress);

}
