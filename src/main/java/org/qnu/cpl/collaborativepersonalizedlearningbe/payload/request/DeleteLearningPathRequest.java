package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import java.util.List;

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
public class DeleteLearningPathRequest {

    private List<String> pathIds;

}
