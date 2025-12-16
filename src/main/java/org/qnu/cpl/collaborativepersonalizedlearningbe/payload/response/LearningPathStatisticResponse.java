package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LearningPathStatisticResponse {

    private String pathId;

    private String pathTitle;

    private Integer totalTopics;

    private Integer totalLessons;

    private Integer completedLessons;

    private Integer remainingLessons;

    private Integer overallProgress;

    private Long durationMonths;

    private Long durationDays;

    private String startDate;

    private String endDate;

    private String lastUpdated;

}

