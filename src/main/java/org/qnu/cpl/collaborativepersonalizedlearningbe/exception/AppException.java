package org.qnu.cpl.collaborativepersonalizedlearningbe.exception;

import lombok.Getter;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;

@Getter
public class AppException extends RuntimeException {

    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
