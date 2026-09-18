package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TopicImportRequest {

    private String title;

    private Integer displayIndex;

    private List<LessonImportRequest> lessons;

}
