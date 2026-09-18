package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import java.time.LocalDateTime;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationSourceType;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
