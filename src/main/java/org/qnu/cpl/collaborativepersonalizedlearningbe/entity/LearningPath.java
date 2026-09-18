package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "learning_paths")
public class LearningPath {

    @Id
    @Column(name = "path_id", columnDefinition = "CHAR(36)")
    private String pathId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LearningStatus status = LearningStatus.NOT_STARTED;

    @Column(name = "progress_percent", nullable = false)
    private Integer progressPercent;

    @Column(name = "is_archived")
    private boolean isArchived;

    @Column(name = "is_favourite")
    private boolean isFavourite;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_owner_id")
    private User originalOwner;

    @Column(name = "original_path_id", columnDefinition = "CHAR(36)")
    private String originalPathId;

    @OneToMany(mappedBy = "learningPath", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningPathTag> learningPathTags;

    @OneToMany(mappedBy = "learningPath", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics;

    public LearningPath clone(User user) {
        LearningPath clone = new LearningPath();
        clone.setPathId(UUIDUtil.generate());
        clone.setTitle(this.title);
        clone.setDescription(this.description);
        clone.setOriginalPathId(this.pathId);
        clone.setOriginalOwner(this.user);
        clone.setStartTime(this.startTime);
        clone.setEndTime(this.endTime);
        clone.setStatus(LearningStatus.NOT_STARTED);
        clone.setProgressPercent(0);
        clone.setArchived(false);
        clone.setFavourite(false);
        clone.setDeleted(false);
        clone.setCreatedAt(LocalDateTime.now());
        clone.setUpdatedAt(LocalDateTime.now());
        clone.setUser(user);

        clone.setTopics(new ArrayList<>());

        return clone;
    }

}
