package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImportLearningPathJsonRequest {

    private MultipartFile file;

}
