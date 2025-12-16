package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateProgressRequest {

    private String lessonId;

    private LearningStatus status;

}
