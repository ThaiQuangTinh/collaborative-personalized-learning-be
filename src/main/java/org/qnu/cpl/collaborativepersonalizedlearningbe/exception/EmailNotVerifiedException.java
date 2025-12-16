package org.qnu.cpl.collaborativepersonalizedlearningbe.exception;

import lombok.Getter;

@Getter
public class EmailNotVerifiedException extends RuntimeException {

    private final String userId;

    private final String email;

    public EmailNotVerifiedException(String userId, String email) {
        super("Email has not been verified yet");
        this.userId = userId;
        this.email = email;
    }

}
