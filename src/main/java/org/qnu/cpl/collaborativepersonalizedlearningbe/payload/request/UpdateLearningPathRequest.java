package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateLearningPathRequest {

    private String title;

    private String description;

}
