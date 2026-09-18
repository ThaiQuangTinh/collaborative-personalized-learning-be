package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import java.time.LocalDateTime;

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
public class UpdatePostResponse {

    private String postId;

    private String title;

    private String content;

    private String externalLink;

    private LocalDateTime updatedAt;

}
