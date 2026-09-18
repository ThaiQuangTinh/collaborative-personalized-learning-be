package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

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
public class UpdateUserProfileRequest {

    private String email;

    private String fullname;

    private String phone;

    private String address;

    private Gender gender;

}
