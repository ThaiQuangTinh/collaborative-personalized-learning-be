package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPath;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.ShareLearningPath;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LearningPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.ShareLearningPathRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.ShareLearningPathService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShareLearningPathServiceImpl implements ShareLearningPathService {

    private final ShareLearningPathRepository shareLearningPathRepository;

    @Override
    public LearningPathResponse getLearningPathByShare(String token) {
        ShareLearningPath shareLearningPath = shareLearningPathRepository
                .findByShareToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.ACCESS_DENIED));

        LearningPath learningPath = shareLearningPath.getLearningPath();

        return new LearningPathResponse(
                learningPath.getPathId(),
                learningPath.getTitle(),
                learningPath.getDescription(),
                learningPath.getStartTime(),
                learningPath.getEndTime(),
                learningPath.getStatus(),
                learningPath.getProgressPercent(),
                learningPath.isArchived(),
                learningPath.isFavourite(),
                learningPath.isDeleted(),
                learningPath.getCreatedAt(),
                null
        );
    }

}
