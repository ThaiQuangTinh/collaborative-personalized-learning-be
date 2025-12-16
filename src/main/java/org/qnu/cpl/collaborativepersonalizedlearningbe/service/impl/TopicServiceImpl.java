package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPath;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Lesson;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Note;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Topic;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.mapper.LessonMapper;
import org.qnu.cpl.collaborativepersonalizedlearningbe.mapper.NoteMapper;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateTopicRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateTopicRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LessonResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TopicResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.LearningPathRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.LessonRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.NoteRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.TopicRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.TopicService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final LearningPathRepository learningPathRepository;

    private final LessonRepository lessonRepository;

    private final NoteRepository noteRepository;

    @Override
    @Transactional
    public TopicResponse createTopic(CreateTopicRequest request) {
        LearningPath learningPath = learningPathRepository.findById(request.getPathId())
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

        if (topicRepository.existsByTitle(request.getTitle())) {
            throw new AppException(ErrorCode.TOPIC_TITLE_ALREADY_EXISTS);
        }

        Topic topic = new Topic();
        int displayIndexMax = topicRepository.findMaxDisplayIndexByPathId(learningPath.getPathId());

        topic.setTopicId(UUIDUtil.generate());
        topic.setLearningPath(learningPath);
        topic.setTitle(request.getTitle());
        topic.setDisplayIndex(displayIndexMax + 1);
        topic.setStatus(LearningStatus.NOT_STARTED);
        topic.setCreatedAt(LocalDateTime.now());
        topic.setUpdatedAt(LocalDateTime.now());

        topicRepository.save(topic);

        return new TopicResponse(
                topic.getTopicId(),
                topic.getTitle(),
                topic.getDisplayIndex(),
                topic.getStartTime(),
                topic.getEndTime(),
                topic.getStatus()
        );
    }

    @Override
    @Transactional
    public TopicResponse updateTopic(String topicId, UpdateTopicRequest request) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new AppException(ErrorCode.TOPIC_NOT_FOUND));

        if (topicRepository.existsByTitle(request.getTitle())) {
            throw new AppException(ErrorCode.TOPIC_TITLE_ALREADY_EXISTS);
        }

        topic.setTitle(request.getTitle());
        topic.setUpdatedAt(LocalDateTime.now());

        topicRepository.save(topic);

        return new TopicResponse(
                topic.getTopicId(),
                topic.getTitle(),
                topic.getDisplayIndex(),
                topic.getStartTime(),
                topic.getEndTime(),
                topic.getStatus()
        );
    }

    @Override
    @Transactional
    public void deleteTopic(String topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new AppException(ErrorCode.TOPIC_NOT_FOUND));

        topicRepository.delete(topic);
    }

    @Override
    public List<LessonResponse> getLessonsByTopicId(String topicId) {
        if (!topicRepository.existsById(topicId)) {
            throw new AppException(ErrorCode.TOPIC_NOT_FOUND);
        }

        List<Lesson> lessonResponses = lessonRepository.findAllByTopic_TopicId(topicId);

        return lessonResponses.stream().map(LessonMapper::toResponse).toList();
    }

    @Override
    public List<NoteResponse> getNotesByTopicId(String topicId) {
        if (!topicRepository.existsById(topicId)) {
            throw new AppException(ErrorCode.TOPIC_NOT_FOUND);
        }

        List<Note> notes = noteRepository.findAllByTargetId(topicId);

        return notes.stream().map(NoteMapper::toResponse).toList();
    }

}
