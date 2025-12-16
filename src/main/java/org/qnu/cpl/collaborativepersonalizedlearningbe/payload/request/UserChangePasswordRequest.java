package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserChangePasswordRequest {

    private String currentPassword;

    private String newPassword;

}
