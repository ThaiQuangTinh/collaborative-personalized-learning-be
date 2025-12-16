package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostCommentResponse {

    private String commentId;

    private UserPostResponse userPostResponse;

    private String content;

    private LocalDateTime updatedAt;

    private boolean ownedByCurrentUser;

}
