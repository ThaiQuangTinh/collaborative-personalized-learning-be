package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterResponse {

    private String userId;

    private String username;

    private String email;

}
