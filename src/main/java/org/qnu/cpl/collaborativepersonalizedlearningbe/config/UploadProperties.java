package org.qnu.cpl.collaborativepersonalizedlearningbe.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {

    private int maxSizeMb;

    private List<String> allowedTypes;

}
