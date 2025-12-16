package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Gender;

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
