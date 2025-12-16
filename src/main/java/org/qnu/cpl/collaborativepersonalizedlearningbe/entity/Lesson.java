package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "lessons")
public class Lesson {

    @Id
    @Column(name = "lesson_id", columnDefinition = "CHAR(36)")
    private String lessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "display_index")
    private Integer displayIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LearningStatus status = LearningStatus.NOT_STARTED;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resource> resources;

    public Lesson clone(Topic newTopic) {
        Lesson clone = new Lesson();
        clone.setLessonId(UUIDUtil.generate());
        clone.setTitle(this.title);
        clone.setDisplayIndex(this.displayIndex);
        clone.setStatus(LearningStatus.NOT_STARTED);
        clone.setStartTime(this.startTime);
        clone.setEndTime(this.endTime);
        clone.setDurationMinutes(this.durationMinutes);
        clone.setCreatedAt(LocalDateTime.now());
        clone.setUpdatedAt(LocalDateTime.now());
        clone.setTopic(newTopic);

        clone.setResources(new ArrayList<>());

        return clone;
    }

}
