package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Role;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminUserResponse {

    private String userId;

    private String username;

    private String email;

    private String fullname;

    private Role role;

    private Boolean emailVerified;

    private LocalDateTime createdAt;

}
