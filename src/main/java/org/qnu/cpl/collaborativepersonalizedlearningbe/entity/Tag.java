package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tags")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tag {

    @Id
    @Column(name = "tag_id", columnDefinition = "CHAR(36)")
    private String tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @Column(name = "text_color", nullable = false)
    private String textColor;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningPathTag> learningPathTags;

}
