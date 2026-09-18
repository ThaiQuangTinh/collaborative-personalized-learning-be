package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends JpaRepository<Topic, String> {

    List<Topic> findAllByLearningPath_PathId(String pathId);

    boolean existsByTitle(String title);

    @Query("SELECT COALESCE(MAX(t.displayIndex), 0) FROM Topic t WHERE t.learningPath.pathId = :pathId")
    int findMaxDisplayIndexByPathId(@Param("pathId") String pathId);

}
