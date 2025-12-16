package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImportLearningPathJsonRequest {

    private MultipartFile file;

}
