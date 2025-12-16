package org.qnu.cpl.collaborativepersonalizedlearningbe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {

    private String userId;

    private String username;

    private Role role;

    private String email;

    private String fullName;

    private String avatarUrl;

    private boolean isVerifiedEmail;

}
