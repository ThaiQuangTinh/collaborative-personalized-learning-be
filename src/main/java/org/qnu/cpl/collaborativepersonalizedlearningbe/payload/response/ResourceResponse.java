package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ResourceType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResourceResponse {

    private String resourceId;

    private String name;

    private ResourceType type;

    private String externalLink;

    private Long sizeBytes;

    private String mimeType;

    private String resourceUrl;

}
