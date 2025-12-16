package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {

    List<Lesson> findAllByTopic_TopicId(String topicId);

    boolean existsByTitleAndTopic_TopicId(String title, String topicId);

    @Query("SELECT COALESCE(MAX(l.displayIndex), 0) FROM Lesson l WHERE l.topic.topicId = :topicId")
    int findMaxDisplayIndexByTopicId(@Param("topicId") String topicId);

}
