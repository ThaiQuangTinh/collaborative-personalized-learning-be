package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.UserInfo;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresIn;

    private UserInfo user;

}
