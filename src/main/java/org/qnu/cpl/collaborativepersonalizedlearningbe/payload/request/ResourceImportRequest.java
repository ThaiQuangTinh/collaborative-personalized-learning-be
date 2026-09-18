package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

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
public class ResourceImportRequest {

    private String resourceId;

    private String name;

    private ResourceType type;

    private String objectName;

    private String externalLink;

    private Long sizeBytes;

    private String mimeType;

    private String originalResourceId;

}
