package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScheduleRequest {

    private LocalDateTime runAt;

    private String message;

}
