package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UploadAvatarRequest {

    private MultipartFile file;

}
