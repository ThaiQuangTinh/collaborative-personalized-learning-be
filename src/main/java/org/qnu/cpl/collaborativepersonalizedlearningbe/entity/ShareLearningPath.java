package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.SharePermission;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "shared_learning_paths")
public class ShareLearningPath {

    @Id
    @Column(name = "share_id", columnDefinition = "CHAR(36)")
    private String shareId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_by_user_id", nullable = false)
    private User sharedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id", nullable = false)
    private LearningPath learningPath;

    @Column(name = "share_token", nullable = false, unique = true)
    private String shareToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "share_permission", nullable = false)
    private SharePermission sharePermission = SharePermission.VIEW;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
