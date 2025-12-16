package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateLearningPathResponse {

    private String pathId;

    private String title;

    private String description;

}
