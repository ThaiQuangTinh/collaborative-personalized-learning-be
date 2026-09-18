package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import java.time.LocalDateTime;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationSourceType;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
