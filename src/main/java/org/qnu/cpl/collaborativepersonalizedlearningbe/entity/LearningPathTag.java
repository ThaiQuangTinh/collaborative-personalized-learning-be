package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
