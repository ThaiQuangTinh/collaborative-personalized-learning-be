package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPath;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPathTag;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPathTagId;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Tag;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLearningPathTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.DeleteLearningPathTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateLearningPathTagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.LearningPathRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.LearningPathTagRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.TagRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.LearningPathTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningPathTagServiceImpl implements LearningPathTagService {

    private final LearningPathRepository learningPathRepository;

    private final TagRepository tagRepository;

    private final LearningPathTagRepository learningPathTagRepository;

    @Override
    @Transactional
    public List<CreateLearningPathTagResponse> createLearningPathTag(
            List<CreateLearningPathTagRequest> requests) {

        List<CreateLearningPathTagResponse> responses = new ArrayList<>();

        for (CreateLearningPathTagRequest request : requests) {
            LearningPath learningPath = learningPathRepository.findById(request.getPathId())
                    .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

            Tag tag = tagRepository.findById(request.getTagId())
                    .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));

            LearningPathTagId learningPathTagId = new LearningPathTagId(request.getPathId(), request.getTagId());
            LearningPathTag learningPathTag = new LearningPathTag();
            learningPathTag.setLearningPathTagId(learningPathTagId);
            learningPathTag.setLearningPath(learningPath);
            learningPathTag.setTag(tag);
            learningPathTag.setCreatedAt(LocalDateTime.now());

            learningPathTagRepository.save(learningPathTag);

            responses.add(new CreateLearningPathTagResponse(
                    learningPath.getPathId(),
                    tag.getTagId(),
                    tag.getTagName()
            ));
        }


        return responses;
    }

    @Override
    @Transactional
    public void deleteLearningPathTag(DeleteLearningPathTagRequest request) {
        LearningPathTagId learningPathTagId = new LearningPathTagId(
                request.getPathId(), request.getTagId()
        );

        if (!learningPathTagRepository.existsById(learningPathTagId)) {
            throw new AppException(ErrorCode.TAG_NOT_ATTACHED_TO_LEARNING_PATH);
        }

        learningPathTagRepository.deleteById(learningPathTagId);
    }

}
