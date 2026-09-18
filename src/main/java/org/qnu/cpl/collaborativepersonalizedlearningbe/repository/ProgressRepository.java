package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.List;
import java.util.Optional;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Lesson;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Progress;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, String> {

    Optional<Progress> findByUser_UserIdAndLesson_LessonId(String userId, String lessonId);

    List<Progress> findAllByUserAndLessonIn(User user, List<Lesson> lessons);

    void deleteByLesson_LessonId(String lessonId);

    List<Progress> findAllByUser_UserIdAndLesson_LessonIdIn(String userId, List<String> lessonIds);

}
