package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LearningPathResponse;

public interface ShareLearningPathService {

    LearningPathResponse getLearningPathByShare(String token);

}
