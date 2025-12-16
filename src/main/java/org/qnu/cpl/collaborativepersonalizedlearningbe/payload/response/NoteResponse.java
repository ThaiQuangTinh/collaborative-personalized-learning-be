package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.TargetType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NoteResponse {

    private String noteId;

    private TargetType targetType;

    private String targetId;

    private String title;

    private String content;

    private Integer displayIndex;

}
