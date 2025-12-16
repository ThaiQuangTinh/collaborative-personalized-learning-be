package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LearningPathImportRequest {

    private String title;

    private String description;

    private List<TopicImportRequest> topics;

}
