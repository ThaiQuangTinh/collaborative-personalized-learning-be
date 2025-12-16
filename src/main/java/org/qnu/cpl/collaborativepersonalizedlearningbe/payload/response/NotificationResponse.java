package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationSourceType;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationResponse {

    private String notificationId;

    private NotificationSourceType sourceType;

    private String sourceId;

    private String message;

    private NotificationType type = NotificationType.GENERAL;

    private String metadata;

    private Boolean isRead = false;

    private LocalDateTime sentAt = LocalDateTime.now();

    private LocalDateTime readAt;

}
