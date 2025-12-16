package org.qnu.cpl.collaborativepersonalizedlearningbe.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {

    private int maxSizeMb;

    private List<String> allowedTypes;

}

