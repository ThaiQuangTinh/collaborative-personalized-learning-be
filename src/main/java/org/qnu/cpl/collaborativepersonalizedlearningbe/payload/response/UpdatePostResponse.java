package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdatePostResponse {

    private String postId;

    private String title;

    private String content;

    private String externalLink;

    private LocalDateTime updatedAt;

}
