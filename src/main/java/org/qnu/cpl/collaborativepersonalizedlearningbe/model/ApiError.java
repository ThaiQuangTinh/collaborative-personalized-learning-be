package org.qnu.cpl.collaborativepersonalizedlearningbe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(Include.NON_NULL)
public class ApiError {

    private String code;

    private String name;

    private String message;

}
