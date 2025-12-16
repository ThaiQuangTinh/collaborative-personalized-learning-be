package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPostResponse {

    private String fullName;

    private String avatarUrl;

}
