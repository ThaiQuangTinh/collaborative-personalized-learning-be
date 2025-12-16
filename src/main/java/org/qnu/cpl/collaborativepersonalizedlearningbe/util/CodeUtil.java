package org.qnu.cpl.collaborativepersonalizedlearningbe.util;

import java.security.SecureRandom;

public class CodeUtil {

    private static final SecureRandom random = new SecureRandom();

    public static String generateVerificationCode() {
        int code = random.nextInt(1_000_000);
        return String.format("%06d", code);
    }

}
