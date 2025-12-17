package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findAllByUser_UserId(String userId);

    List<Notification> findAllByUser_UserIdAndIsReadFalse(String userId);

    List<Notification> findTop3ByUser_UserIdOrderBySentAtDesc(String userId);

    long countByUser_UserIdAndIsReadFalse(String userId);

    void deleteAllByUser_UserId(String userId);

    Optional<Notification> findByUser_UserId(String userId);
}
