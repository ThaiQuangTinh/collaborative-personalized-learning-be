package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPath;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LearningPathRepository extends JpaRepository<LearningPath, String> {

    boolean existsByTitle(String title);

    Optional<LearningPath> findByUser_UserIdAndPathIdAndIsDeletedFalse(String userId, String pathId);

    Optional<LearningPath> findByPathIdAndIsDeletedFalse(String pathId);

    List<LearningPath> findAllByUser_UserIdAndIsArchivedFalseAndIsDeletedFalseOrderByCreatedAtDesc(String userId);

    List<LearningPath> findAllByUser_UserIdAndIsFavouriteTrueAndIsDeletedFalseAndIsArchivedFalse(String userId);

    List<LearningPath> findAllByUser_UserIdAndIsArchivedTrueAndIsDeletedFalse(String userId);

    boolean existsByUser_UserId(String userId);

    List<LearningPath> findAllByUser(User user);

    List<LearningPath> findAllByUserAndIsArchivedFalseAndIsDeletedFalse(User user);

    boolean existsByUser_UserIdAndOriginalPathId(String userId, String pathId);

}
