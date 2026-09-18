package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NotificationResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UnreadNotificationCountResponse;

public interface NotificationService {

    void sendLikeNotification(String userId, String postId, String fromUserId);

    void sendCommentNotification(String userId, String postId, String commentId, String fromUserId);

    void deleteNotificationById(String notificationId);

    void markAsReadNotificationById(String notificationId);

    void markAsAllReadNotificationByUserId(String userId);

    List<NotificationResponse> getLatestNotificationsByUserId(String userId);

    UnreadNotificationCountResponse getUnreadCountNotification(String userId);

    void deleteAllNotificationByUserId(String userId);

    void sendLessonReminderNotification(String userId, String lessonId, String pathId);

}
