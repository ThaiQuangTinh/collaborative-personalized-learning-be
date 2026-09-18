package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import java.time.LocalDateTime;

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
public class CreateLessonRequest {

    private String topicId;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
