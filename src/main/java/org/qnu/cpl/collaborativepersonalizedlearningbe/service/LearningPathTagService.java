package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLearningPathTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.DeleteLearningPathTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateLearningPathTagResponse;

import java.util.List;


public interface LearningPathTagService {

    List<CreateLearningPathTagResponse> createLearningPathTag(
            List<CreateLearningPathTagRequest> requests);

    void deleteLearningPathTag(DeleteLearningPathTagRequest request);

}
