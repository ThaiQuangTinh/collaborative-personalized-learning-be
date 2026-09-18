package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLearningPathTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.DeleteLearningPathTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateLearningPathTagResponse;

public interface LearningPathTagService {

    List<CreateLearningPathTagResponse> createLearningPathTag(
            List<CreateLearningPathTagRequest> requests);

    void deleteLearningPathTag(DeleteLearningPathTagRequest request);

}
