package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private long expiresIn;

}
