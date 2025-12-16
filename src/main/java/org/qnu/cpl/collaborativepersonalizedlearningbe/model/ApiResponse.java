package org.qnu.cpl.collaborativepersonalizedlearningbe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {

    private int status;

    private boolean success;

    private String message;

    private T data;

    private ApiError error;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private String path;

}
