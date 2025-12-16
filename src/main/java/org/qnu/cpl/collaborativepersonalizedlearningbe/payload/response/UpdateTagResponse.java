package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateTagResponse {

    private String tagId;

    private String tagName;

    private String textColor;

}
