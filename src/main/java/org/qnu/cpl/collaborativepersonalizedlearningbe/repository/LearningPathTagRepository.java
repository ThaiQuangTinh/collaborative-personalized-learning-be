package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPathTag;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPathTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningPathTagRepository extends JpaRepository<LearningPathTag, LearningPathTagId> {

    List<LearningPathTag> findAllByLearningPath_PathId(String pathId);

}
