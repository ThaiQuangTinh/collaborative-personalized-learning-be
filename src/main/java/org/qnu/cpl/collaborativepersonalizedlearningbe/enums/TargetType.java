package org.qnu.cpl.collaborativepersonalizedlearningbe.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TargetType {
    PATH,
    TOPIC,
    LESSON;

    @JsonCreator
    public static TargetType fromString(String value) {
        if (value == null) return null;
        return TargetType.valueOf(value.toUpperCase());
    }
}
