package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import java.time.LocalDateTime;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Gender;

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
