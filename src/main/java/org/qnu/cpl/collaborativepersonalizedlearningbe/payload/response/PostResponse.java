package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostResponse {

    private String postId;

    private UserPostResponse userPostResponse;

    private String title;

    private String content;

    private String externalLink;

    private Integer likeCount = 0;

    private Integer commentCount = 0;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;

    private List<PostCommentResponse> postCommentResponses;

    private boolean isOwnedByCurrentUser;

    private boolean isLiked;

}
