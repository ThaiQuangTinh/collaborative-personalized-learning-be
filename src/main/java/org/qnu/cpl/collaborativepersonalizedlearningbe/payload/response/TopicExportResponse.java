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
public class TopicExportResponse {

    private String title;

    private Integer displayIndex;

    private List<LessonExportResponse> lessons;

}
