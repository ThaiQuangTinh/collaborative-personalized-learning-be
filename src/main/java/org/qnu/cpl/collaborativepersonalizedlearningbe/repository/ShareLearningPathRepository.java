package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.ShareLearningPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShareLearningPathRepository extends JpaRepository<ShareLearningPath, String> {

    Optional<ShareLearningPath> findByShareToken(String token);

    boolean existsByLearningPath_PathId(String pathId);

    Optional<ShareLearningPath> findByLearningPath_PathId(String pathId);

}

