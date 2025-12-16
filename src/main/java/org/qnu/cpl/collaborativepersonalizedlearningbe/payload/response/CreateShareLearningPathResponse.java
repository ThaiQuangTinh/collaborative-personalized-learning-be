package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.SharePermission;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateShareLearningPathResponse {

    private String shareUrl;

    private String token;

    private SharePermission sharePermission;

}
