package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import java.time.LocalDateTime;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Role;

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
public class AdminUserResponse {

    private String userId;

    private String username;

    private String email;

    private String fullname;

    private Role role;

    private Boolean emailVerified;

    private LocalDateTime createdAt;

}
