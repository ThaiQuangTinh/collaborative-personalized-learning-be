package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateLessonRequest {

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
