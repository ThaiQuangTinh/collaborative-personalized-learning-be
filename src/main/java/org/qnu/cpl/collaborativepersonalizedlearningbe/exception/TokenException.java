package org.qnu.cpl.collaborativepersonalizedlearningbe.exception;

import lombok.Getter;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;

import org.springframework.security.core.AuthenticationException;

@Getter
public class TokenException extends AuthenticationException {

    private final ErrorCode errorCode;

    public TokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
