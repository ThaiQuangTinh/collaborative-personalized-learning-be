package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TopicResponse {

    private String topicId;

    private String title;

    private Integer displayIndex;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LearningStatus status = LearningStatus.NOT_STARTED;

}
