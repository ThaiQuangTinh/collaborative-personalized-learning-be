package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyResetPasswordCodeResponse {

    private String token;

}
