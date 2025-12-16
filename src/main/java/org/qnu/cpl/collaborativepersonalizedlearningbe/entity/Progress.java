package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "progress")
public class Progress {

    @Id
    @Column(name = "progress_id", columnDefinition = "CHAR(36)")
    private String progressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LearningStatus status = LearningStatus.NOT_STARTED;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
