package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
