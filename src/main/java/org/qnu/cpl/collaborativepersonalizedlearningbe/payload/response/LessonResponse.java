package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import java.time.LocalDateTime;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonResponse {

    private String lessonId;

    private String title;

    private Integer displayIndex;

    private LearningStatus status = LearningStatus.NOT_STARTED;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isLocked;

}
