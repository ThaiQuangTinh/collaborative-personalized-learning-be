package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ResourceType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateLinkResourceResponse {

    private String resourceId;

    private String name;

    private ResourceType type;

    private String externalLink;

    private LocalDateTime createdAt;

}
