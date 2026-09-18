package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ResourceType;

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
public class ResourceResponse {

    private String resourceId;

    private String name;

    private ResourceType type;

    private String externalLink;

    private Long sizeBytes;

    private String mimeType;

    private String resourceUrl;

}
