package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateTagResponse {

    private String tagId;

    private String tagName;

    private String textColor;

}
