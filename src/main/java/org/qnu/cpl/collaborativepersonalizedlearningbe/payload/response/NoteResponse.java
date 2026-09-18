package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.TargetType;

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
public class NoteResponse {

    private String noteId;

    private TargetType targetType;

    private String targetId;

    private String title;

    private String content;

    private Integer displayIndex;

}
