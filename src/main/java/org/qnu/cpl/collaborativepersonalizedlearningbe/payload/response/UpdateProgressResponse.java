package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

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
public class UpdateProgressResponse {

    private String progressId;

    private String lessonId;

    private LearningStatus status;

}
