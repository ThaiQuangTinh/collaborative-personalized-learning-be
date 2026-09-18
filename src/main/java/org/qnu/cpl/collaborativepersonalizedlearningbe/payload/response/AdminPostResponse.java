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
public class AdminPostResponse {

    private String postId;

    private String title;

    private String content;

    private String authorName;

    private Integer likeCount;

    private Integer commentCount;

    private LocalDateTime createdAt;

}
