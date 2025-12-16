package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.TargetType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "notes")
public class Note {

    @Id
    @Column(name = "note_id", columnDefinition = "CHAR(36)")
    private String noteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType targetType;

    @Column(name = "target_id", nullable = false)
    private String targetId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "display_index")
    private Integer displayIndex;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
