package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPathTag;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPathTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningPathTagRepository extends JpaRepository<LearningPathTag, LearningPathTagId> {

    List<LearningPathTag> findAllByLearningPath_PathId(String pathId);

}
