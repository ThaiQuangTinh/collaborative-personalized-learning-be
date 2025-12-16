package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LearningPathExportResponse {

    private String title;

    private String description;

    private List<TopicExportResponse> topics;

}
