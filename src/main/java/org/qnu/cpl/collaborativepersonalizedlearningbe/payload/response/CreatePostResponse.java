package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreatePostResponse {

    private String postId;

    private UserPostResponse userPostResponse;

    private String title;

    private String content;

    private String externalLink;

    private Integer likeCount = 0;

    private Integer commentCount = 0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
