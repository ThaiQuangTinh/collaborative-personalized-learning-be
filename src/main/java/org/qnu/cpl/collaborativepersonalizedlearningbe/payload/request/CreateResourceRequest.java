package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ResourceType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateResourceRequest {

    private String lessonId;

    private String name;

    private ResourceType type;

}
