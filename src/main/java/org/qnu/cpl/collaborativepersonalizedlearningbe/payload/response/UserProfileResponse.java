package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Gender;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Role;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserProfileResponse {

    private String userId;

    private String email;

    private String fullname;

    private String avatarUrl;

    private String phone;

    private String address;

    private Gender gender;

    private Boolean emailVerified;

    private LocalDateTime createdAt;

}
