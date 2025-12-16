package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeleteLearningPathRequest {

    private List<String> pathIds;

}
