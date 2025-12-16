package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LessonExportResponse {

    private String title;

    private Integer displayIndex;

    private List<ResourceExportResponse> resources;

}
