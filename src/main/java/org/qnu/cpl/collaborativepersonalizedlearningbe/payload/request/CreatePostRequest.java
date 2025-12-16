package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreatePostRequest {

    private String title;

    private String content;

    private String externalLink;

}
