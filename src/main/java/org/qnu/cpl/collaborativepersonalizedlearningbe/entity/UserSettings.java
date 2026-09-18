package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import java.time.LocalDateTime;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Language;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Theme;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings {

    @Id
    @Column(name = "setting_id", columnDefinition = "CHAR(36)")
    private String settingId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme", nullable = false)
    private Theme theme = Theme.AUTO;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language = Language.VI;

    @Column(name = "notificationEnabled", nullable = false)
    private boolean notificationEnabled = false;

    @Column(name = "lessonReminderMinutes", nullable = false)
    private Integer lessonReminderMinutes = 60;

    @Column(name = "emailNotificationEnabled", nullable = false)
    private boolean emailNotificationEnabled = false;

    @Column(name = "pushNotificationEnabled", nullable = false)
    private boolean pushNotificationEnabled = false;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
