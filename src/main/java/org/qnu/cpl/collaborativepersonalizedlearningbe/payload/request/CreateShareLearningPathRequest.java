package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.SharePermission;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateShareLearningPathRequest {

    private SharePermission sharePermission;

}
