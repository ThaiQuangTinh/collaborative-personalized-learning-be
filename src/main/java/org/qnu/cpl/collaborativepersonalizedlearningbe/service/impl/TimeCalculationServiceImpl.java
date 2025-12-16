package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPath;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Lesson;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Topic;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.LearningPathRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.TopicRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.TimeCalculationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TimeCalculationServiceImpl implements TimeCalculationService {

    private final TopicRepository topicRepository;

    private final LearningPathRepository learningPathRepository;

    @Override
    public void recalcTopicTime(Topic topic) {

        // FRESH FETCH
        Topic fresh = topicRepository.findById(topic.getTopicId())
                .orElseThrow();

        List<Lesson> lessons = fresh.getLessons();

        if (lessons == null || lessons.isEmpty()) {
            fresh.setStartTime(null);
            fresh.setEndTime(null);
        } else {
            LocalDateTime minStart = lessons.stream()
                    .map(Lesson::getStartTime)
                    .filter(Objects::nonNull)
                    .min(LocalDateTime::compareTo)
                    .orElse(null);

            LocalDateTime maxEnd = lessons.stream()
                    .map(Lesson::getEndTime)
                    .filter(Objects::nonNull)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);

            fresh.setStartTime(minStart);
            fresh.setEndTime(maxEnd);
        }

        topicRepository.save(fresh);
    }


    @Override
    public void recalcLearningPathTime(LearningPath path) {

        // FRESH FETCH
        LearningPath fresh = learningPathRepository.findById(path.getPathId())
                .orElseThrow();

        List<Topic> topics = fresh.getTopics();

        if (topics == null || topics.isEmpty()) {
            fresh.setStartTime(null);
            fresh.setEndTime(null);
        } else {
            LocalDateTime minStart = topics.stream()
                    .map(Topic::getStartTime)
                    .filter(Objects::nonNull)
                    .min(LocalDateTime::compareTo)
                    .orElse(null);

            LocalDateTime maxEnd = topics.stream()
                    .map(Topic::getEndTime)
                    .filter(Objects::nonNull)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);

            fresh.setStartTime(minStart);
            fresh.setEndTime(maxEnd);
        }

        learningPathRepository.save(fresh);
    }

}
