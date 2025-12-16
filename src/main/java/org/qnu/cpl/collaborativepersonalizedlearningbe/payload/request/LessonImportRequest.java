package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LessonImportRequest {

    private String title;

    private Integer displayIndex;

    private List<ResourceImportRequest> resources;

}
