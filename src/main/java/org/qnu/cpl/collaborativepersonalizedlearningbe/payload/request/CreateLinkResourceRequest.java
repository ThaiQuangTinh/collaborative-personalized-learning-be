package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateLinkResourceRequest {

    private String lessonId;

    private String name;

    private String externalLink;

}
