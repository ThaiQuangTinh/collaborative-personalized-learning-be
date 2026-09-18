package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.Optional;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.ShareLearningPath;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareLearningPathRepository extends JpaRepository<ShareLearningPath, String> {

    Optional<ShareLearningPath> findByShareToken(String token);

    boolean existsByLearningPath_PathId(String pathId);

    Optional<ShareLearningPath> findByLearningPath_PathId(String pathId);

}
