package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

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
public class UpdateNoteRequest {

    private String title;

    private String content;

}
