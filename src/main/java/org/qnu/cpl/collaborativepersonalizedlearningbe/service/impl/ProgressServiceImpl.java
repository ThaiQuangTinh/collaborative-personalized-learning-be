package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Lesson;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Progress;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateProgressRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateProgressRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateProgressResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdateProgressResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.LessonRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.ProgressRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.ProgressService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;

    private final UserRepository userRepository;

    private final LessonRepository lessonRepository;

    @Override
    @Transactional
    public CreateProgressResponse createProgress(String userId, CreateProgressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Progress progress = new Progress();
        progress.setProgressId(UUIDUtil.generate());
        progress.setUser(user);
        progress.setLesson(lesson);
        progress.setStatus(request.getStatus());
        progress.setUpdatedAt(LocalDateTime.now());

        progressRepository.save(progress);

        return new CreateProgressResponse(
                progress.getProgressId(),
                progress.getLesson().getLessonId(),
                progress.getStatus()
        );
    }

    @Override
    @Transactional
    public UpdateProgressResponse updateProgress(String userId, UpdateProgressRequest request) {
        Progress progress = progressRepository.findByUser_UserIdAndLesson_LessonId(userId, request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.PROGRESS_NOT_FOUND));

        progress.setStatus(request.getStatus());
        progress.setUpdatedAt(LocalDateTime.now());

        progressRepository.save(progress);
        progressRepository.flush();

        // Update status for lesson
        Lesson lesson = progress.getLesson();
        lesson.setStatus(progress.getStatus());

        lessonRepository.save(lesson);
        lessonRepository.flush();

        return new UpdateProgressResponse(
                progress.getProgressId(),
                progress.getLesson().getLessonId(),
                progress.getStatus()
        );
    }

    @Override
    @Transactional
    public void deleteProgress(String lessonId) {
        progressRepository.deleteByLesson_LessonId(lessonId);
    }

    @Override
    public void saveProgress(Progress progress) {
        progressRepository.save(progress);
    }

}
