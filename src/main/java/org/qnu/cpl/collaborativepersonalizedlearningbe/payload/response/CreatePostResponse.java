package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
