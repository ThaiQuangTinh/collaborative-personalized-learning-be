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
@Table(name = "topics")
public class Topic {

    @Id
    @Column(name = "topic_id", columnDefinition = "CHAR(36)")
    private String topicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id", nullable = false)
    private LearningPath learningPath;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name="display_index")
    private Integer displayIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LearningStatus status = LearningStatus.NOT_STARTED;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;

    public Topic clone(LearningPath newLearningPath) {
        Topic clone = new Topic();
        clone.setTopicId(UUIDUtil.generate());
        clone.setTitle(this.title);
        clone.setStartTime(this.startTime);
        clone.setEndTime(this.endTime);
        clone.setDisplayIndex(this.displayIndex);
        clone.setStatus(LearningStatus.NOT_STARTED);
        clone.setCreatedAt(LocalDateTime.now());
        clone.setUpdatedAt(LocalDateTime.now());
        clone.setLearningPath(newLearningPath);

        clone.setLessons(new ArrayList<>());

        return clone;
    }

}
