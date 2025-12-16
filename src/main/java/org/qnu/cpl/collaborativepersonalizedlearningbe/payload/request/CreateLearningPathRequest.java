package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateLearningPathRequest {

    private String title;

    private String description;

}
