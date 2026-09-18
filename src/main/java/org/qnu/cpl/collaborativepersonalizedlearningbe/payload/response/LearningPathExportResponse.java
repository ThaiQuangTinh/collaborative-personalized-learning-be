package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import java.util.List;

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
public class LearningPathExportResponse {

    private String title;

    private String description;

    private List<TopicExportResponse> topics;

}
