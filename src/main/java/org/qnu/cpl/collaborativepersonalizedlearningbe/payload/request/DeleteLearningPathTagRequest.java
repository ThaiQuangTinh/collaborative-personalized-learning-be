package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeleteLearningPathTagRequest {

    private String pathId;

    private String tagId;

}
