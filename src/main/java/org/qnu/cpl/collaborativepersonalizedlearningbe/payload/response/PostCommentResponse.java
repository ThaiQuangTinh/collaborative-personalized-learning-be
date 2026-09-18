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
public class PostCommentResponse {

    private String commentId;

    private UserPostResponse userPostResponse;

    private String content;

    private LocalDateTime updatedAt;

    private boolean ownedByCurrentUser;

}
