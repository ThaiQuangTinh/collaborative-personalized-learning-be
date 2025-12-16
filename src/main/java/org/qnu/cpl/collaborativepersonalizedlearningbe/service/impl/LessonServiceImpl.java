package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Lesson;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Note;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Topic;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.mapper.NoteMapper;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLessonRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateProgressRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateLessonRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.LessonRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.NoteRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.TopicRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.LessonService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.ProgressService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.ResourceService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.TimeCalculationService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final TopicRepository topicRepository;

    private final NoteRepository noteRepository;

    private final TimeCalculationService timeCalculationService;

    private final ResourceService resourceService;

    private final ProgressService progressService;

    @Override
    public LessonResponse createLesson(String userId, CreateLessonRequest request) {
        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new AppException(ErrorCode.TOPIC_NOT_FOUND));

        if (lessonRepository.existsByTitleAndTopic_TopicId(request.getTitle(), request.getTopicId())) {
            throw new AppException(ErrorCode.LESSON_TITLE_ALREADY_EXISTS);
        }

        Integer maxDisplayIndex = lessonRepository.findMaxDisplayIndexByTopicId(request.getTopicId());

        Lesson lesson = new Lesson();
        lesson.setLessonId(UUIDUtil.generate());
        lesson.setTopic(topic);
        lesson.setTitle(request.getTitle());
        lesson.setStartTime(request.getStartTime());
        lesson.setEndTime(request.getEndTime());
        lesson.setDisplayIndex(maxDisplayIndex + 1);
        lesson.setCreatedAt(LocalDateTime.now());
        lesson.setUpdatedAt(LocalDateTime.now());

        lessonRepository.save(lesson);

        // Recalculate topic + path.
        timeCalculationService.recalcTopicTime(topic);
        timeCalculationService.recalcLearningPathTime(topic.getLearningPath());

        // Create progress default for lesson.
        progressService.createProgress(
                userId, new CreateProgressRequest(lesson.getLessonId(), LearningStatus.NOT_STARTED));

        return new LessonResponse(
                lesson.getLessonId(),
                lesson.getTitle(),
                lesson.getDisplayIndex(),
                lesson.getStatus(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                false
        );
    }

    @Override
    public LessonResponse updateLesson(String lessonId, UpdateLessonRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.TOPIC_NOT_FOUND));

        lesson.setTitle(request.getTitle());
        lesson.setStartTime(request.getStartTime());
        lesson.setEndTime(request.getEndTime());
        lesson.setUpdatedAt(LocalDateTime.now());

        lessonRepository.save(lesson);

        timeCalculationService.recalcTopicTime(lesson.getTopic());
        timeCalculationService.recalcLearningPathTime(lesson.getTopic().getLearningPath());

        return new LessonResponse(
                lesson.getLessonId(),
                lesson.getTitle(),
                lesson.getDisplayIndex(),
                lesson.getStatus(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                false
        );
    }

    @Override
    @Transactional
    public void deleteLesson(String lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        String topicId = lesson.getTopic().getTopicId();

        progressService.deleteProgress(lessonId);

        lessonRepository.delete(lesson);
        lessonRepository.flush();

        // Fetch fresh
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new AppException(ErrorCode.TOPIC_NOT_FOUND));

        timeCalculationService.recalcTopicTime(topic);
        timeCalculationService.recalcLearningPathTime(topic.getLearningPath());
    }


    @Override
    public List<NoteResponse> getNotesByLessonId(String lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new AppException(ErrorCode.LESSON_NOT_FOUND);
        }

        List<Note> notes = noteRepository.findAllByTargetId(lessonId);

        return notes.stream().map(NoteMapper::toResponse).toList();
    }

    @Override
    public List<ResourceResponse> getAllResourcesByLessonId(String lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new AppException(ErrorCode.LESSON_NOT_FOUND);
        }

        return  resourceService.getAllResourcesByLessonId(lessonId);
    }

}
