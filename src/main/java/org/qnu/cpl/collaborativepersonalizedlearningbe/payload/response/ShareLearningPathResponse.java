package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.SharePermission;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShareLearningPathResponse {

    private String shareByUserId;

    private String pathId;

    private SharePermission sharePermission;

}
