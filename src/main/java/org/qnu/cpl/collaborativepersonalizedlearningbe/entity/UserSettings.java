package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Language;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Theme;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@Builder
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
    @Column(nullable = false)
    private Theme theme = Theme.AUTO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language = Language.VI;

    @Column(nullable = false)
    private boolean notificationEnabled = false;

    @Column(nullable = false)
    private Integer lessonReminderMinutes = 60;

    @Column(nullable = false)
    private boolean emailNotificationEnabled = false;

    @Column(nullable = false)
    private boolean pushNotificationEnabled = false;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
