package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "learning_path_tags")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LearningPathTag {

    @EmbeddedId
    private LearningPathTagId learningPathTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pathId")
    @JoinColumn(name = "path_id")
    private LearningPath learningPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
