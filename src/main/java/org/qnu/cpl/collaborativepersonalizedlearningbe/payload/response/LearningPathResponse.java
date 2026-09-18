package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import java.time.LocalDateTime;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
