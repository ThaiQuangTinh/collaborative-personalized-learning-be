package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Notification;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationSourceType;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationType;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.mapper.NotificationMapper;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NotificationResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UnreadNotificationCountResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.NotificationRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.PostRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.NotificationService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void sendLikeNotification(String userId, String postId, String fromUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!postRepository.existsById(postId)) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Map<String, Object> meta = Map.of(
                "postId", postId,
                "fromUserId", fromUserId
        );

        Notification noti = new Notification();
        noti.setNotificationId(UUIDUtil.generate());
        noti.setUser(user);
        noti.setMessage(fromUser.getFullname() + " đã thích bài viết của bạn");
        noti.setSourceId(postId);
        noti.setType(NotificationType.POST_LIKED);
        noti.setSourceType(NotificationSourceType.POST);
        noti.setIsRead(false);
        noti.setSentAt(LocalDateTime.now());

        try {
            noti.setMetadata(objectMapper.writeValueAsString(meta));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize notification metadata", e);
        }

        notificationRepository.save(noti);

        NotificationResponse response = NotificationMapper.toResponse(noti);

        messagingTemplate.convertAndSendToUser(
                userId,
                "/queue/notifications",
                response
        );

        System.out.println("Sent notification to user " + userId);
    }

    @Override
    @Transactional
    public void sendCommentNotification(String userId, String postId, String commentId,String fromUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!postRepository.existsById(postId)) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Map<String, Object> meta = Map.of(
                "postId", postId,
                "commentId", commentId,
                "fromUserId", fromUserId
        );

        Notification noti = new Notification();
        noti.setNotificationId(UUIDUtil.generate());
        noti.setUser(user);
        noti.setMessage(fromUser.getFullname() + " đã bình luận bài viết của bạn");
        noti.setSourceId(postId);
        noti.setType(NotificationType.POST_COMMENTED);
        noti.setSourceType(NotificationSourceType.POST);
        noti.setIsRead(false);
        noti.setSentAt(LocalDateTime.now());

        try {
            noti.setMetadata(objectMapper.writeValueAsString(meta));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize notification metadata", e);
        }

        notificationRepository.save(noti);

        NotificationResponse response = NotificationMapper.toResponse(noti);

        messagingTemplate.convertAndSendToUser(
                userId,
                "/queue/notifications",
                response
        );


        System.out.println("Sent notification to user " + userId);
    }

    @Override
    @Transactional
    public void deleteNotificationById(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notificationRepository.delete(notification);
    }

    @Override
    @Transactional
    public void markAsReadNotificationById(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAsAllReadNotificationByUserId(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        List<Notification> notifications = notificationRepository.findAllByUser_UserIdAndIsReadFalse(userId);

        LocalDateTime now = LocalDateTime.now();
        for (Notification n : notifications) {
            n.setIsRead(true);
            n.setReadAt(now);
        }

        notificationRepository.saveAll(notifications);
    }

    @Override
    public List<NotificationResponse> getLatestNotificationsByUserId(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        List<Notification> notifications =
                notificationRepository.findTop3ByUser_UserIdOrderBySentAtDesc(userId);

        return notifications.stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    @Override
    public UnreadNotificationCountResponse getUnreadCountNotification(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        long unreadCount = notificationRepository.countByUser_UserIdAndIsReadFalse(userId);

        return new UnreadNotificationCountResponse(unreadCount);
    }

    @Override
    @Transactional
    public void deleteAllNotificationByUserId(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        notificationRepository.deleteAllByUser_UserId(userId);
    }

    // Đến hạn hoàn thành lộ trình
//    {
//        "roadmapId": "r456",
//            "dueDate": "2025-01-05"
//    }

    // Hoàn thành lộ trình
//    {
//        "roadmapId": "r456",
//            "dueDate": "2025-01-05"
//    }

}
