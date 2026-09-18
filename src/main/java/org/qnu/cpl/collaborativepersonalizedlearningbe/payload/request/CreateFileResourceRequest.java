package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import org.springframework.web.multipart.MultipartFile;

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
public class CreateFileResourceRequest {

    private String lessonId;

    private String name;

    private MultipartFile file;

}
