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
public class CreateResourceRequest {

    private String lessonId;

    private String name;

    private ResourceType type;

}
