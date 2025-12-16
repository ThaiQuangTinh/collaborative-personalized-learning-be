package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationSourceType;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "notifications")
public class Notification {

    @Id
    @Column(name = "notification_id", columnDefinition = "CHAR(36)")
    private String notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false)
    private NotificationSourceType sourceType;

    @Column(name = "source_id", columnDefinition = "CHAR(36)")
    private String sourceId;

    @Column(name = "message", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type = NotificationType.GENERAL;

    @Column(name = "metadata", columnDefinition = "JSON")
    private String metadata;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();

    @Column(name = "read_at")
    private LocalDateTime readAt;

}
