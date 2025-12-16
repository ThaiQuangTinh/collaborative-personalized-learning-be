package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LearningPathTagId implements Serializable {

    private String pathId;

    private String tagId;

}
