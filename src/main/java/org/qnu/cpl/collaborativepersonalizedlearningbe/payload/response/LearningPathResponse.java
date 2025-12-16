package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LearningPathResponse {

    private String pathId;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LearningStatus status;

    private Integer progressPercent;

    private boolean isArchived;

    private boolean isFavourite;

    private boolean isDeleted;

    private LocalDateTime createAt;

    private UserOriginalPathResponse userOriginalPathResponse;

}
