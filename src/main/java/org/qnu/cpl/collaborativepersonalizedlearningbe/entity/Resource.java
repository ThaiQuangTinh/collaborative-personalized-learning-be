package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ResourceType;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "resources")
public class Resource {

    @Id
    @Column(name = "resource_id", columnDefinition = "CHAR(36)")
    private String resourceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private ResourceType type;

    @Column(name = "object_name", columnDefinition = "TEXT")
    private String objectName;

    @Column(name = "external_link", columnDefinition = "TEXT")
    private String externalLink;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Column(name = "original_resource_id", columnDefinition = "CHAR(36)")
    private String originalResourceId;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Resource clone(Lesson newLesson, User newUser) {
        Resource clone = new Resource();
        clone.setResourceId(UUIDUtil.generate());
        clone.setLesson(newLesson);
        clone.setUser(newUser);
        clone.setName(this.name);
        clone.setType(this.type);
        clone.setObjectName(this.objectName);
        clone.setExternalLink(this.externalLink);
        clone.setSizeBytes(this.sizeBytes);
        clone.setMimeType(this.mimeType);
        clone.setOriginalResourceId(this.resourceId);
        clone.setIsDeleted(false);
        clone.setCreatedAt(LocalDateTime.now());
        clone.setUpdatedAt(LocalDateTime.now());
        return clone;
    }

}
