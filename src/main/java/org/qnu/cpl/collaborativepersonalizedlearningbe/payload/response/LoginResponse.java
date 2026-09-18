package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import org.qnu.cpl.collaborativepersonalizedlearningbe.model.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
